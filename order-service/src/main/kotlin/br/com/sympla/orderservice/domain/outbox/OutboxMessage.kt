package br.com.sympla.orderservice.domain.outbox

import java.time.Instant

data class OutboxMessage(val id: Long,
                         val createdAt: Instant,
                         val event: String,
                         val payload: Map<String, Any>) {

    fun <T> emitEvent(payloadToEvent: (Any, Class<*>) -> T): T {
        return payloadToEvent(this.payload, Class.forName(this.event))
    }
}
