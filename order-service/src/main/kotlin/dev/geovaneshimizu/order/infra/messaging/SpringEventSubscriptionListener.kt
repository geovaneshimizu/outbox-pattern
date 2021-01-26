package dev.geovaneshimizu.order.infra.messaging

import dev.geovaneshimizu.order.domain.order.SubscriptionCreated
import dev.geovaneshimizu.order.domain.order.SubscriptionListener
import mu.KotlinLogging
import org.springframework.context.event.EventListener

class SpringEventSubscriptionListener(private val subscriptionListener: SubscriptionListener) : SubscriptionListener {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @EventListener
    override fun onSubscribed(event: SubscriptionCreated) {
        logger.info { "Forwarding $event" }
        this.subscriptionListener.onSubscribed(event)
    }
}
