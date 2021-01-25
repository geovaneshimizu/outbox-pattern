package dev.geovaneshimizu.subscription.infra.db

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.jdbc.metadata.JdbcMetadataStore
import org.springframework.integration.metadata.ConcurrentMetadataStore
import javax.sql.DataSource

@Configuration
class DbConfig {

    @Bean
    fun idempotencyKeyStore(dataSource: DataSource): ConcurrentMetadataStore {
        return JdbcMetadataStore(dataSource)
    }
}
