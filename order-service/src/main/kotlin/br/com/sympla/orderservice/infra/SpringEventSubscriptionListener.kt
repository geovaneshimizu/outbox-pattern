package br.com.sympla.orderservice.infra

import br.com.sympla.orderservice.domain.PublishSubscriptionCreated
import br.com.sympla.orderservice.domain.SubscriptionCreated
import br.com.sympla.orderservice.domain.SubscriptionListener
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SpringEventSubscriptionListener(private val publishSubscriptionCreated: PublishSubscriptionCreated) :
    SubscriptionListener {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @EventListener
    override fun onSubscribed(event: SubscriptionCreated) {
        this.publishSubscriptionCreated.publish(event)
        logger.info { "Consumed $event event" }
    }
}
