package dev.geovaneshimizu.subscriptionservice.infra.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import dev.geovaneshimizu.subscriptionservice.domain.subscription.NewSubscription
import dev.geovaneshimizu.subscriptionservice.domain.subscription.NewSubscriptionListener
import dev.geovaneshimizu.subscriptionservice.domain.subscription.Subscriptions
import dev.geovaneshimizu.subscriptionservice.infra.spring.getPayloadMessageAs
import mu.KotlinLogging
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

@Component
class SqsNewSubscriptionListener(private val subscriptions: Subscriptions,
                                 private val objectMapper: ObjectMapper) : NewSubscriptionListener<Message<String>> {

    companion object {

        const val newSubscriptionsQueue = "subscription-service.infra.aws.sqs.new-subscriptions-queue"

        val logger = KotlinLogging.logger { }
    }

    @SqsListener(value = ["\${$newSubscriptionsQueue}"])
    override fun listen(message: Message<String>) {
        logger.info { "Received new $message" }

        val newSubscription = message.getPayloadMessageAs<NewSubscription>(this.objectMapper)
        logger.info { "Adding $newSubscription" }

        this.subscriptions.addSubscription(newSubscription.valuesToAdd())
    }
}
