package dev.geovaneshimizu.subscription.app.api.messaging

import dev.geovaneshimizu.subscription.domain.subscription.NewSubscriptionListener
import mu.KotlinLogging
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

@Component
class SqsNewSubscriptionListener(private val newSubscriptionListener: NewSubscriptionListener<Message<String>>) {

    companion object {

        const val newSubscriptionsQueue = "subscription-service.infra.aws.sqs.new-subscriptions-queue"

        val logger = KotlinLogging.logger { }
    }

    @SqsListener(value = ["\${$newSubscriptionsQueue}"])
    fun listen(message: Message<String>) {
        logger.info { "Received $message" }
        this.newSubscriptionListener.listen(message)
    }
}
