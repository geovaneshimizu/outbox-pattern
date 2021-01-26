package dev.geovaneshimizu.order.app.job

import dev.geovaneshimizu.order.domain.outbox.OutboxPolling
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SpringScheduledAndAsyncOutboxPolling(private val outboxPolling: OutboxPolling) {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @Scheduled(
            initialDelayString = "\${order-service.app.job.outbox-polling.initial-delay-in-milli}",
            fixedRateString = "\${order-service.app.job.outbox-polling.fixed-rate-in-milli}"
    )
    @Async
    fun runScheduled() {
        logger.info { "Scheduled outbox polling" }
        this.outboxPolling.consumeOutboxMessages()
    }
}
