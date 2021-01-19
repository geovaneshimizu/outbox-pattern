package dev.geovaneshimizu.subscriptionservice.domain.subscription

import java.time.Instant
import java.util.*

data class Subscription(val id: Long,
                        val createdAt: Instant,
                        val purchaseOrderId: UUID,
                        val expiresAt: Instant,
                        val userEmail: String,
                        val sectorId: Long,
                        val seatId: Long)
