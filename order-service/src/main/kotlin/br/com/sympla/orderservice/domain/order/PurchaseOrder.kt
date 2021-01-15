package br.com.sympla.orderservice.domain.order

import java.time.Instant
import java.util.UUID

data class PurchaseOrder(val id: Long,
                         val externalId: UUID,
                         val createdAt: Instant,
                         val userEmail: String,
                         val eventId: Long,
                         val sectorId: Long,
                         val seatId: Long)
