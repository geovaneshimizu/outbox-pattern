package dev.geovaneshimizu.subscription.app

import com.fasterxml.jackson.databind.ObjectMapper
import dev.geovaneshimizu.subscription.domain.subscription.IdempotentNewSubscriptionReceiver
import dev.geovaneshimizu.subscription.domain.subscription.NewSubscriptionListener
import dev.geovaneshimizu.subscription.domain.subscription.SubscriptionRepository
import dev.geovaneshimizu.subscription.infra.db.JdbcSubscriptionRepository
import dev.geovaneshimizu.subscription.infra.messaging.SpringIntegrationIdempotentNewSubscriptionReceiver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.jdbc.metadata.JdbcMetadataStore
import org.springframework.integration.metadata.ConcurrentMetadataStore
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.messaging.Message
import javax.sql.DataSource

@Configuration
class AppConfig {

    @Bean
    fun <T> newSubscriptionListener(newSubscriptionReceiver: IdempotentNewSubscriptionReceiver<T>,
                                    subscriptionRepository: SubscriptionRepository): NewSubscriptionListener<T> {
        return NewSubscriptionListener(newSubscriptionReceiver, subscriptionRepository)
    }

    @Bean
    fun idempotentNewSubscriptionReceiver(objectMapper: ObjectMapper,
                                          idempotencyKeyStore: ConcurrentMetadataStore): IdempotentNewSubscriptionReceiver<Message<String>> {
        return SpringIntegrationIdempotentNewSubscriptionReceiver(objectMapper, idempotencyKeyStore)
    }

    @Bean
    fun idempotencyKeyStore(dataSource: DataSource): ConcurrentMetadataStore {
        return JdbcMetadataStore(dataSource)
    }

    @Bean
    fun subscriptionRepository(jdbcTemplate: JdbcTemplate): SubscriptionRepository {
        return JdbcSubscriptionRepository(jdbcTemplate)
    }
}
