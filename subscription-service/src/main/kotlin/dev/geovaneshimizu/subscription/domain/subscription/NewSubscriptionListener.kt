package dev.geovaneshimizu.subscription.domain.subscription

import mu.KotlinLogging

class NewSubscriptionListener<T>(private val newSubscriptionReceiver: IdempotentNewSubscriptionReceiver<T>,
                                 private val subscriptionRepository: SubscriptionRepository) {

    companion object {
        val logger = KotlinLogging.logger { }
    }

    fun listen(message: T) {
        logger.info { "Evaluating $message" }
        this.newSubscriptionReceiver.acceptNewSubscription(message) { newSubscription ->
            this.subscriptionRepository.insert(newSubscription.valuesToAdd())
                    .also { logger.info { "Inserted $it" } }
        } ?: logger.info { "Duplicated $message; Discarding..." }
    }
}
