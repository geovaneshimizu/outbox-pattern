package dev.geovaneshimizu.order.domain.outbox

interface OutboxProcessor {

    fun consumeOutbox()
}
