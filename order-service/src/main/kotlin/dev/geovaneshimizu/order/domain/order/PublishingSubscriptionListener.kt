package dev.geovaneshimizu.order.domain.order

import mu.KotlinLogging

class PublishingSubscriptionListener(private val publishSubscriptionCreated: PublishSubscriptionCreated) : SubscriptionListener {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    override fun onSubscribed(event: SubscriptionCreated) {
        this.publishSubscriptionCreated.publish(event)
        logger.info { "Published event $event" }
    }
}
