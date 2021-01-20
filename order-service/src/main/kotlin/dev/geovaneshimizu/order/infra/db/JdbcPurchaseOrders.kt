package dev.geovaneshimizu.order.infra.db

import dev.geovaneshimizu.order.domain.order.PlaceOrderValues
import dev.geovaneshimizu.order.domain.order.PurchaseOrder
import dev.geovaneshimizu.order.domain.order.PurchaseOrders
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@Component
class JdbcPurchaseOrders(private val jdbcTemplate: JdbcTemplate,
                         private val jsonbMapper: JsonbMapper,
                         transactionManager: PlatformTransactionManager) : PurchaseOrders {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    private val transactionTemplate = TransactionTemplate(transactionManager)

    override fun placeOrder(values: PlaceOrderValues): PurchaseOrder? {
        return this.transactionTemplate.execute { transactionStatus ->
            try {
                val purchaseOrder =
                        InsertPurchaseOrder(this.jdbcTemplate, values)()
                                .also { logger.info { "Inserted $it" } }

                values.associatedEvents()
                        .map { it(purchaseOrder) }
                        .map { it.messageDraft() }
                        .forEach {
                            InsertOutboxMessage(this.jdbcTemplate, this.jsonbMapper, it)()
                                    .also { logger.info { "Inserted $it" } }
                        }

                purchaseOrder
            } catch (e: Exception) {
                logger.error(e) { "Place order error: ${e.message}" }
                transactionStatus.setRollbackOnly()
                null
            }
        }
    }
}
