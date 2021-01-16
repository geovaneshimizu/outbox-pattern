package dev.geovaneshimizu.subscriptionservice.infra.messaging

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsConfig {

    @Bean
    fun queueMessagingTemplate(amazonSqs: AmazonSQSAsync): QueueMessagingTemplate =
            QueueMessagingTemplate(amazonSqs)

    @Bean
    fun amazonSqs(awsProperties: AwsProperties): AmazonSQSAsync =
            AmazonSQSAsyncClientBuilder.standard()
                    .withCredentials(awsProperties.credentials)
                    .withEndpointConfiguration(awsProperties.endpointConfiguration())
                    .build()
}
