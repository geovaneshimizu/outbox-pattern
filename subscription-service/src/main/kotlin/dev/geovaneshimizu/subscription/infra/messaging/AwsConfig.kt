package dev.geovaneshimizu.subscription.infra.messaging

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AwsConfig {

    @Bean
    fun amazonSqs(awsProperties: AwsProperties): AmazonSQSAsync =
            AmazonSQSAsyncClientBuilder.standard()
                    .withCredentials(awsProperties.credentials)
                    .withEndpointConfiguration(awsProperties.endpointConfiguration())
                    .build()
}
