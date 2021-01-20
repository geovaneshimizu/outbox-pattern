package dev.geovaneshimizu.order.infra.messaging

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("order-service.infra.aws")
data class AwsProperties(val credentials: Credentials,
                         val endpoint: String,
                         val region: String,
                         val sns: Sns) {

    fun endpointConfiguration(): AwsClientBuilder.EndpointConfiguration {
        return AwsClientBuilder.EndpointConfiguration(this.endpoint, this.region)
    }

    data class Credentials(val accessKey: String,
                           val secretKey: String) : AWSCredentialsProvider {

        override fun getCredentials(): AWSCredentials {
            return BasicAWSCredentials(this.accessKey, this.secretKey)
        }

        override fun refresh() {
            // Nothing to do
        }
    }

    data class Sns(val subscriptionCreatedTopic: String)
}
