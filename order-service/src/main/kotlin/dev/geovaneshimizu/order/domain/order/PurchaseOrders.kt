package dev.geovaneshimizu.order.domain.order

import dev.geovaneshimizu.order.domain.outbox.OutboxRepository
import mu.KotlinLogging

class PurchaseOrders(private val purchaseOrderRepository: PurchaseOrderRepository,
                     private val outboxRepository: OutboxRepository) {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    fun placeOrder(values: PlaceOrderValues): PurchaseOrder? {
        return this.purchaseOrderRepository.executeInTransaction {
            val purchaseOrder =
                    purchaseOrderRepository.insert(values)
                            .also { logger.info { "Inserted $it" } }

            values.associatedEvents()
                    .map { it(purchaseOrder) }
                    .map { it.messageDraft() }
                    .forEach {
                        this.outboxRepository.insert(it)
                                .also { logger.info { "Inserted $it" } }
                    }

            purchaseOrder
        }
    }
}
