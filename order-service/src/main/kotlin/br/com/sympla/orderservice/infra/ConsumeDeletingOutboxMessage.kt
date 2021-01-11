package br.com.sympla.orderservice.infra

import br.com.sympla.orderservice.domain.OutboxMessage
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate

class ConsumeDeletingOutboxMessage(private val jdbcTemplate: JdbcTemplate,
                                   private val jsonbMapper: JsonbMapper,
                                   private val eventConsumer: (Any) -> Unit,
                                   transactionManager: PlatformTransactionManager) : Runnable {

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

    override fun run() {
        this.transactionTemplate.execute(object : TransactionCallbackWithoutResult() {

            override fun doInTransactionWithoutResult(status: TransactionStatus) {
                try {
                    val headOutboxMessage = deleteMessage()
                    logger.info { "Deleted $headOutboxMessage" }

                    if (headOutboxMessage == null) {
                        status.setRollbackOnly()
                        return
                    }

                    val event = extractEvent(headOutboxMessage)
                    logger.info { "Extracted $event" }

                    eventConsumer(event)
                } catch (ex: Exception) {
                    status.setRollbackOnly()
                }
            }
        })
    }

    private fun deleteMessage(): OutboxMessage? {
        return this.jdbcTemplate.queryForObject(deleteScript) { resultSet, _ ->
            OutboxMessage(
                    id = resultSet.getLong("id"),
                    createdAt = resultSet.getInstant("created_at"),
                    event = resultSet.getString("event"),
                    payload = resultSet.getJsonb("payload", this.jsonbMapper))
        }
    }

    private fun extractEvent(outboxMessage: OutboxMessage): Any {
        return this.jsonbMapper.fromAny(outboxMessage.payload, Class.forName(outboxMessage.event))
    }
}
