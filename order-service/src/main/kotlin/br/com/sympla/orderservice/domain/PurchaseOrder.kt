package br.com.sympla.orderservice.domain

import java.time.Instant
import java.util.*

data class PurchaseOrder(private val id: Long,
                         val publicId: UUID,
                         val createdAt: Instant,
                         val userEmail: String,
                         val eventId: Long,
                         val sectorId: Long,
                         val seatId: Long)
