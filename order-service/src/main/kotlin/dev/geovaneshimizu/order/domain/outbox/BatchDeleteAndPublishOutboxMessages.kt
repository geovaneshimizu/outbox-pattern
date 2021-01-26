package dev.geovaneshimizu.order.domain.outbox

import mu.KotlinLogging

class BatchDeleteAndPublishOutboxMessages(private val outboxRepository: OutboxRepository,
                                          private val payloadToEvent: OutboxMessagePayloadToEvent<*>,
                                          private val eventPublisher: (Any) -> Unit,
                                          private val messagesToDelete: Int) : OutboxPollingStrategy {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    override fun pull() {
        this.outboxRepository.executeInTransactionWithoutResult {
            val outboxMessages = this.outboxRepository.deleteFirsts(messagesToDelete)

            if (outboxMessages.isEmpty()) {
                logger.info { "No outbox message to consume; Returning" }
            } else {
                logger.info { "Deleted ${outboxMessages.size} outbox messages" }

                outboxMessages
                        .map {
                            it.emitEvent(payloadToEvent)
                                    .also { logger.info { "Emitted $it" } }
                        }
                        .forEach {
                            it?.run(eventPublisher)
                        }
            }
        }
    }
}
