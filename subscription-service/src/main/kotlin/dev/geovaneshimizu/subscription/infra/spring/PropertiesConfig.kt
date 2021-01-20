package dev.geovaneshimizu.subscription.infra.spring

import dev.geovaneshimizu.subscription.SubscriptionServiceApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = [SubscriptionServiceApplication::class])
class PropertiesConfig
