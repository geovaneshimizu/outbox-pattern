package dev.geovaneshimizu.order.infra.messaging

import dev.geovaneshimizu.order.domain.order.PublishSubscriptionCreated
import dev.geovaneshimizu.order.domain.order.SubscriptionCreated
import dev.geovaneshimizu.order.domain.order.SubscriptionListener
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SpringEventSubscriptionListener(private val publishSubscriptionCreated: PublishSubscriptionCreated) : SubscriptionListener {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @EventListener
    override fun onSubscribed(event: SubscriptionCreated) {
        this.publishSubscriptionCreated.publish(event)
        logger.info { "Consumed $event event" }
    }
}
