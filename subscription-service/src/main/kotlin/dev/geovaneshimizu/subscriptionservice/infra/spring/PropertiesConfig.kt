package dev.geovaneshimizu.subscriptionservice.infra.spring

import dev.geovaneshimizu.subscriptionservice.SubscriptionServiceApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = [SubscriptionServiceApplication::class])
class PropertiesConfig
