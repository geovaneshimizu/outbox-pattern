package dev.geovaneshimizu.order.infra.db

import dev.geovaneshimizu.order.domain.outbox.NewMessageValues
import dev.geovaneshimizu.order.domain.outbox.OutboxMessage
import dev.geovaneshimizu.order.domain.outbox.OutboxRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

class JdbcOutboxRepository(private val jdbcTemplate: JdbcTemplate,
                           private val jsonbMapper: JsonbMapper,
                           transactionManager: PlatformTransactionManager) : OutboxRepository {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    private val transactionTemplate = TransactionTemplate(transactionManager)

    override fun insert(values: NewMessageValues): OutboxMessage {
        logger.info { "Inserting $values" }
        return InsertOutboxMessage(this.jdbcTemplate, this.jsonbMapper)(values)
    }

    override fun deleteFirsts(quantity: Int): List<OutboxMessage> {
        logger.info { "Deleting first $quantity messages" }
        return DeleteOutboxMessages(this.jdbcTemplate, this.jsonbMapper)(quantity)
    }

    override fun <T> executeInTransactionWithResult(block: () -> T?): T? {
        return this.transactionTemplate.execute { transactionStatus ->
            try {
                block()
            } catch (e: Exception) {
                logger.error(e) { "Outbox transaction error: ${e.message}" }
                transactionStatus.setRollbackOnly()
                null
            }
        }
    }

    override fun executeInTransactionWithoutResult(block: () -> Unit) {
        return this.transactionTemplate.executeWithoutResult { transactionStatus ->
            try {
                block()
            } catch (e: Exception) {
                logger.error(e) { "Outbox transaction error: ${e.message}" }
                transactionStatus.setRollbackOnly()
            }
        }
    }
}
