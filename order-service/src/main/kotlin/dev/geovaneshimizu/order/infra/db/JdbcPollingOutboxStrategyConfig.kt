package dev.geovaneshimizu.order.infra.db

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class JdbcPollingOutboxStrategyConfig {

    @Bean
    fun fifoDeleteAndPublishOutboxMessage(jdbcTemplate: JdbcTemplate,
                                          transactionManager: PlatformTransactionManager,
                                          jsonbMapper: JsonbMapper,
                                          eventPublisher: ApplicationEventPublisher): JdbcPollingOutboxStrategy {
        return FifoDeleteAndPublishOutboxMessage(
                jdbcTemplate = jdbcTemplate,
                jsonbMapper = jsonbMapper,
                eventPublisher = eventPublisher::publishEvent,
                transactionManager = transactionManager)
    }

    @Bean
    @Primary
    fun batchDeleteAndPublishOutboxMessage(jdbcTemplate: JdbcTemplate,
                                           transactionManager: PlatformTransactionManager,
                                           jsonbMapper: JsonbMapper,
                                           eventPublisher: ApplicationEventPublisher,
                                           @Value("\${order-service.infra.delete-and-publish-outbox-message.batch-size}")
                                           batchSize: Int): JdbcPollingOutboxStrategy {
        return BatchDeleteAndPublishOutboxMessage(
                jdbcTemplate = jdbcTemplate,
                jsonbMapper = jsonbMapper,
                eventPublisher = eventPublisher::publishEvent,
                batchSize = batchSize,
                transactionManager = transactionManager)
    }
}
