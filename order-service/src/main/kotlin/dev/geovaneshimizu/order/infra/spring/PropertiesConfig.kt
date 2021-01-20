package dev.geovaneshimizu.order.infra.spring

import dev.geovaneshimizu.order.OrderServiceApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = [OrderServiceApplication::class])
class PropertiesConfig
