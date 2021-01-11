package br.com.sympla.orderservice.infra

import br.com.sympla.orderservice.domain.SubscriptionCreated
import br.com.sympla.orderservice.domain.SubscriptionListener
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SpringEventSubscriptionListener : SubscriptionListener {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @EventListener
    override fun onSubscribed(event: SubscriptionCreated) {
        logger.info { "Consumed $event event" }
    }
}
