package br.com.sympla.orderservice.infra.messaging

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.sns.AmazonSNSAsync
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsConfig {

    @Bean
    fun notificationMessagingTemplate(amazonSns: AmazonSNSAsync): NotificationMessagingTemplate =
        NotificationMessagingTemplate(amazonSns)

    @Bean
    fun amazonSns(awsCredentialsProvider: AWSCredentialsProvider,
                  awsProperties: AwsProperties): AmazonSNSAsync =
        AmazonSNSAsyncClientBuilder.standard()
            .withCredentials(awsCredentialsProvider)
            .withEndpointConfiguration(EndpointConfiguration(awsProperties.endpoint, awsProperties.region))
            .build()
}
