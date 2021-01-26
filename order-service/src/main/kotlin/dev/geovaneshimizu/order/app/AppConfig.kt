package dev.geovaneshimizu.order.app

import dev.geovaneshimizu.order.domain.order.PurchaseOrderRepository
import dev.geovaneshimizu.order.domain.order.PurchaseOrders
import dev.geovaneshimizu.order.domain.outbox.*
import dev.geovaneshimizu.order.infra.db.JdbcOutboxRepository
import dev.geovaneshimizu.order.infra.db.JdbcPurchaseOrderRepository
import dev.geovaneshimizu.order.infra.db.JsonbMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class AppConfig {

    @Bean
    fun purchaseOrders(purchaseOrderRepository: PurchaseOrderRepository,
                       outboxRepository: OutboxRepository): PurchaseOrders {
        return PurchaseOrders(purchaseOrderRepository, outboxRepository)
    }

    @Bean
    fun purchaseOrderRepository(jdbcTemplate: JdbcTemplate,
                                transactionManager: PlatformTransactionManager): PurchaseOrderRepository {
        return JdbcPurchaseOrderRepository(jdbcTemplate, transactionManager)
    }

    @Bean
    fun outboxRepository(jdbcTemplate: JdbcTemplate,
                         jsonbMapper: JsonbMapper,
                         transactionManager: PlatformTransactionManager): OutboxRepository {
        return JdbcOutboxRepository(jdbcTemplate, jsonbMapper, transactionManager)
    }

    @Bean
    fun outboxPolling(batchDeleteAndPublishOutboxMessages: OutboxPollingStrategy): OutboxPolling {
        return OutboxPolling(batchDeleteAndPublishOutboxMessages)
    }

    @Bean
    fun batchDeleteAndPublishOutboxMessages(outboxRepository: OutboxRepository,
                                            jsonbMapper: JsonbMapper,
                                            eventPublisher: ApplicationEventPublisher,
                                            @Value("\${order-service.app.job.outbox-polling.messages-to-delete}")
                                            messagesToDelete: Int): OutboxPollingStrategy {

        val payloadToEvent = object : OutboxMessagePayloadToEvent<Any> {

            override fun invoke(obj: Any, clazz: Class<*>): Any {
                return jsonbMapper.fromAny(obj, clazz)
            }
        }

        return BatchDeleteAndPublishOutboxMessages(
                outboxRepository = outboxRepository,
                payloadToEvent = payloadToEvent,
                eventPublisher = eventPublisher::publishEvent,
                messagesToDelete = messagesToDelete)
    }
}
