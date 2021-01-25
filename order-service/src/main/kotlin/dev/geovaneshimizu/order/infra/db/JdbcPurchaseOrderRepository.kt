package dev.geovaneshimizu.order.infra.db

import dev.geovaneshimizu.order.domain.order.PlaceOrderValues
import dev.geovaneshimizu.order.domain.order.PurchaseOrder
import dev.geovaneshimizu.order.domain.order.PurchaseOrderRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

class JdbcPurchaseOrderRepository(private val jdbcTemplate: JdbcTemplate,
                                  transactionManager: PlatformTransactionManager) : PurchaseOrderRepository {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    private val transactionTemplate = TransactionTemplate(transactionManager)

    override fun insert(values: PlaceOrderValues): PurchaseOrder {
        return InsertPurchaseOrder(this.jdbcTemplate, values)()
    }

    override fun <T> executeInTransaction(block: () -> T?): T? {
        return this.transactionTemplate.execute { transactionStatus ->
            try {
                block()
            } catch (e: Exception) {
                logger.error(e) { "Purchase order transaction error: ${e.message}" }
                transactionStatus.setRollbackOnly()
                null
            }
        }
    }
}
