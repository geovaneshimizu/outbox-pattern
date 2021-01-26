package dev.geovaneshimizu.order.domain.outbox

import mu.KotlinLogging

class OutboxPolling(private val strategy: OutboxPollingStrategy) {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    fun consumeOutboxMessages() {
        logger.info { "Consuming outbox messages" }
        this.strategy.pull()
    }
}
