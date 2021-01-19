package br.com.sympla.orderservice.infra.messaging

import com.amazonaws.services.sns.AmazonSNSAsync
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.cloud.aws.core.env.ResourceIdResolver
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.MessageConverter

@Configuration
class AwsConfig {

    @Bean
    fun notificationMessagingTemplate(amazonSns: AmazonSNSAsync,
                                      resourceIdResolver: ResourceIdResolver,
                                      mappingJackson2MessageConverter: MessageConverter): NotificationMessagingTemplate =
        NotificationMessagingTemplate(amazonSns, resourceIdResolver, mappingJackson2MessageConverter)

    @Bean
    fun amazonSns(awsProperties: AwsProperties): AmazonSNSAsync =
        AmazonSNSAsyncClientBuilder.standard()
            .withCredentials(awsProperties.credentials)
            .withEndpointConfiguration(awsProperties.endpointConfiguration())
            .build()

    @Bean
    fun mappingJackson2MessageConverter(objectMapper: ObjectMapper): MessageConverter {
        val jackson2MessageConverter = MappingJackson2MessageConverter()
        jackson2MessageConverter.objectMapper = objectMapper
        jackson2MessageConverter.serializedPayloadClass = String::class.java
        return jackson2MessageConverter
    }
}
