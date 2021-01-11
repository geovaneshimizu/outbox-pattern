package br.com.sympla.orderservice.infra

import br.com.sympla.orderservice.domain.OutboxProcessor
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class JdbcPollingOutboxProcessor(private val consumeStrategy: JdbcConsumeOutboxStrategy) : OutboxProcessor {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @Scheduled(
            fixedRateString = "\${order-service.infra.polling-outbox-processor.fixed-rate-in-milli}",
            initialDelayString = "\${order-service.infra.polling-outbox-processor.initial-delay-in-milli}"
    )
    @Async
    override fun consumeOutbox() {
        logger.info { "Polling outbox message" }
        this.consumeStrategy.consume()
    }
}
