package br.com.sympla.orderservice.domain.outbox

data class NewMessageValues(val event: String,
                            val payload: Any)
