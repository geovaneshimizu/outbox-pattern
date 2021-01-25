package dev.geovaneshimizu.subscription.infra.messaging

import dev.geovaneshimizu.subscription.domain.subscription.IdempotentNewSubscriptionProcessor
import dev.geovaneshimizu.subscription.domain.subscription.NewSubscriptionListener
import dev.geovaneshimizu.subscription.domain.subscription.SubscriptionRepository
import mu.KotlinLogging
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

@Component
class SqsNewSubscriptionListener(private val subscriptionRepository: SubscriptionRepository,
                                 private val messageProcessor: IdempotentNewSubscriptionProcessor<Message<String>>) :
        NewSubscriptionListener<Message<String>> {

    companion object {

        const val newSubscriptionsQueue = "subscription-service.infra.aws.sqs.new-subscriptions-queue"

        val logger = KotlinLogging.logger { }
    }

    @SqsListener(value = ["\${$newSubscriptionsQueue}"])
    override fun listen(message: Message<String>) {
        logger.info { "Received $message" }

        this.messageProcessor.acceptIfNotExists(message) { newSubscription ->
            logger.info { "Adding $newSubscription" }
            this.subscriptionRepository.addSubscription(newSubscription.valuesToAdd())
        }
    }
}
