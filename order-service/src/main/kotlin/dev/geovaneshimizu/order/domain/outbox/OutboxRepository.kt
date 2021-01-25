package dev.geovaneshimizu.order.domain.outbox

interface OutboxRepository {

    fun insert(values: NewMessageValues): OutboxMessage
}
