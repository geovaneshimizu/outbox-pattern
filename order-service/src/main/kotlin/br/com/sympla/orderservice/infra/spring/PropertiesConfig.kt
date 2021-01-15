package br.com.sympla.orderservice.infra.spring

import br.com.sympla.orderservice.OrderServiceApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = [OrderServiceApplication::class])
class PropertiesConfig
