package br.com.sympla.orderservice.domain

import java.time.Instant

data class OutboxMessage(val id: Long,
                         val createdAt: Instant,
                         val event: String,
                         val payload: Map<String, Any>)
