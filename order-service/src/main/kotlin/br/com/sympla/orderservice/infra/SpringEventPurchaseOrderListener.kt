package br.com.sympla.orderservice.infra

import br.com.sympla.orderservice.domain.PurchaseOrderCreated
import br.com.sympla.orderservice.domain.PurchaseOrderListener
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
        logger.info { "Consumed $event event" }
    }
}
