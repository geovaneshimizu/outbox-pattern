package dev.geovaneshimizu.order.domain.outbox

import dev.geovaneshimizu.order.domain.TransactionalRepository

interface OutboxRepository : TransactionalRepository {

    fun insert(values: NewMessageValues): OutboxMessage

    fun deleteFirsts(quantity: Int): List<OutboxMessage>
}
