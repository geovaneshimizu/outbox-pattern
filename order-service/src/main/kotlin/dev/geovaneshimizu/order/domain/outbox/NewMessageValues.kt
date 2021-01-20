package dev.geovaneshimizu.order.domain.outbox

data class NewMessageValues(val event: String,
                            val payload: Any)
