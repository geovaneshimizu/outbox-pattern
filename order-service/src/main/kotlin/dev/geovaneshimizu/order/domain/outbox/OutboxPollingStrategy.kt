package dev.geovaneshimizu.order.domain.outbox

interface OutboxPollingStrategy {

    fun pull()
}
