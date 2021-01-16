package dev.geovaneshimizu.subscriptionservice.infra.messaging

import mu.KotlinLogging
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.Message
import org.springframework.stereotype.Component

@Component
class SqsSubscriptionCreatedListener {

    companion object {

        const val newSubscriptionsQueue = "subscription-service.infra.aws.sqs.new-subscriptions-queue"

        val logger = KotlinLogging.logger { }
    }

    @SqsListener(value = ["\${$newSubscriptionsQueue}"])
    fun listen(message: Message<*>) {
        logger.info { "Got new $message" }
    }
}
