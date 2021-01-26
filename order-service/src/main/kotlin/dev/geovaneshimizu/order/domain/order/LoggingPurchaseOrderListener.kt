package dev.geovaneshimizu.order.domain.order

import mu.KotlinLogging

class LoggingPurchaseOrderListener : PurchaseOrderListener {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    override fun onCreated(event: PurchaseOrderCreated) {
        logger.info { "Just logging $event" }
        // TODO create a metric for this event
    }
}
