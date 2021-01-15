package br.com.sympla.orderservice.infra

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("order-service.infra.aws")
data class AwsProperties(val endpoint: String,
                         val region: String,
                         val topic: Topic) {

    data class Topic(val subscriptionCreated: String)
}
