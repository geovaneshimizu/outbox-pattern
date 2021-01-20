package dev.geovaneshimizu.order.infra.messaging

import dev.geovaneshimizu.order.domain.order.PurchaseOrderCreated
import dev.geovaneshimizu.order.domain.order.PurchaseOrderListener
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SpringEventPurchaseOrderListener : PurchaseOrderListener {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @EventListener
    override fun onCreated(event: PurchaseOrderCreated) {
        logger.info { "Just logging $event" }
        // TODO create a metric for this event
    }
}
