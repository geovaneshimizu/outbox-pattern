package dev.geovaneshimizu.order.api

import dev.geovaneshimizu.order.domain.order.PurchaseOrderRepository
import dev.geovaneshimizu.order.domain.order.PurchaseOrders
import dev.geovaneshimizu.order.domain.outbox.OutboxRepository
import dev.geovaneshimizu.order.infra.db.JdbcOutboxRepository
import dev.geovaneshimizu.order.infra.db.JdbcPurchaseOrderRepository
import dev.geovaneshimizu.order.infra.db.JsonbMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ApiConfig {

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
                         jsonbMapper: JsonbMapper): OutboxRepository {
        return JdbcOutboxRepository(jdbcTemplate, jsonbMapper)
    }
}
