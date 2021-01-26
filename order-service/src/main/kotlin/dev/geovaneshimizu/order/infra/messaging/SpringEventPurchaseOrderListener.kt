package dev.geovaneshimizu.order.infra.messaging

import dev.geovaneshimizu.order.domain.order.PurchaseOrderCreated
import dev.geovaneshimizu.order.domain.order.PurchaseOrderListener
import mu.KotlinLogging
import org.springframework.context.event.EventListener

class SpringEventPurchaseOrderListener(private val purchaseOrderListener: PurchaseOrderListener) : PurchaseOrderListener {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @EventListener
    override fun onCreated(event: PurchaseOrderCreated) {
        logger.info { "Forwarding $event" }
        this.purchaseOrderListener.onCreated(event)
    }
}
