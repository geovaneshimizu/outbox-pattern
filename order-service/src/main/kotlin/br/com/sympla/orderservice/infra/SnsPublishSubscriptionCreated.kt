package br.com.sympla.orderservice.infra

import br.com.sympla.orderservice.domain.PublishSubscriptionCreated
import br.com.sympla.orderservice.domain.SubscriptionCreated
import mu.KotlinLogging
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate
import org.springframework.stereotype.Component

@Component
class SnsPublishSubscriptionCreated(private val notification: NotificationMessagingTemplate,
                                    awsProperties: AwsProperties) :
    PublishSubscriptionCreated {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    private val subscriptionCreatedTopic = awsProperties.topic.subscriptionCreated

    override fun publish(event: SubscriptionCreated) {
        logger.info { "Publishing $event to ${this.subscriptionCreatedTopic} topic" }
        this.notification.sendNotification(this.subscriptionCreatedTopic, event, "subscription.created")
    }
}
