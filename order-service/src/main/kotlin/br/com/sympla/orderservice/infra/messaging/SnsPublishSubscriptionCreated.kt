package br.com.sympla.orderservice.infra.messaging

import br.com.sympla.orderservice.domain.order.PublishSubscriptionCreated
import br.com.sympla.orderservice.domain.order.SubscriptionCreated
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

    private val subscriptionCreatedTopic = awsProperties.sns.subscriptionCreatedTopic

    override fun publish(event: SubscriptionCreated) {
        logger.info { "Publishing $event to ${this.subscriptionCreatedTopic} topic" }
        this.notification.sendNotification(this.subscriptionCreatedTopic, event, "subscription.created")
    }
}
