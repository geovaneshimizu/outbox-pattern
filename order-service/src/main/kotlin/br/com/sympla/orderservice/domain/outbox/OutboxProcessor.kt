package br.com.sympla.orderservice.domain.outbox

interface OutboxProcessor {

    fun consumeOutbox()
}
