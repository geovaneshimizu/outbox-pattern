package dev.geovaneshimizu.subscription.infra.messaging

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.jdbc.metadata.JdbcMetadataStore
import org.springframework.integration.metadata.ConcurrentMetadataStore
import javax.sql.DataSource

@Configuration
class SpringIntegrationConfig {

    @Bean
    fun idempotencyKeyStore(dataSource: DataSource): ConcurrentMetadataStore {
        return JdbcMetadataStore(dataSource)
    }
}
