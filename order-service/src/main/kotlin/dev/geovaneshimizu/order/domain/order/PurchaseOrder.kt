package dev.geovaneshimizu.order.domain.order

import java.time.Instant
import java.util.*

data class PurchaseOrder(val id: Long,
                         val externalId: UUID,
                         val createdAt: Instant,
                         val userEmail: String,
                         val eventId: Long,
                         val sectorId: Long,
                         val seatId: Long)
