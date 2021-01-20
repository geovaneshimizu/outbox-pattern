package dev.geovaneshimizu.subscription.domain.subscription

import java.time.LocalDateTime
import java.util.*

data class AddSubscriptionValues(val purchaseOrderId: UUID,
                                 val expiresAt: LocalDateTime,
                                 val userEmail: String,
                                 val sectorId: Long,
                                 val seatId: Long)
