package br.com.sympla.orderservice.infra.db

import br.com.sympla.orderservice.domain.outbox.OutboxMessage
import mu.KotlinLogging
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate

class FifoDeleteAndPublishOutboxMessage(private val jdbcTemplate: JdbcTemplate,
                                        private val jsonbMapper: JsonbMapper,
                                        private val eventPublisher: (Any) -> Unit,
                                        transactionManager: PlatformTransactionManager) : JdbcPollingOutboxStrategy {

    companion object {
        private val logger = KotlinLogging.logger { }

        private val deleteScript: String = """
            DELETE FROM order_service.outbox 
              WHERE id = (
                SELECT id
                  FROM order_service.outbox
                  ORDER BY id
                  FOR UPDATE SKIP LOCKED
                  LIMIT 1
              )
              RETURNING id, created_at, event, payload
        """.trimIndent()
    }

    private val transactionTemplate = TransactionTemplate(transactionManager)

    override fun pull() {
        this.transactionTemplate.execute(object : TransactionCallbackWithoutResult() {

            override fun doInTransactionWithoutResult(status: TransactionStatus) {
                try {
                    val headOutboxMessage = deleteMessage()

                    if (headOutboxMessage == null) {
                        logger.info { "No outbox message to consume; Returning" }
                        status.setRollbackOnly()
                        return
                    }

                    logger.info { "Deleted $headOutboxMessage" }

                    val event =
                        headOutboxMessage.emitEvent { any, clazz -> jsonbMapper.fromAny(any, clazz) }
                            .also { logger.info { "Emitted $it" } }

                    eventPublisher(event)
                } catch (ex: Exception) {
                    logger.error { "$ex; Rolling back deleted message" }
                    status.setRollbackOnly()
                }
            }
        })
    }

    private fun deleteMessage(): OutboxMessage? {
        return try {
            this.jdbcTemplate.queryForObject(deleteScript) { resultSet, _ ->
                OutboxMessage(
                    id = resultSet.getLong("id"),
                    createdAt = resultSet.getInstant("created_at"),
                    event = resultSet.getString("event"),
                    payload = resultSet.getJsonb("payload", this.jsonbMapper))
            }
        } catch (e: DataRetrievalFailureException) {
            logger.info { "No outbox message retrieved due: $e" }
            null
        }
    }
}
