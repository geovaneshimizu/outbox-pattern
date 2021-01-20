package dev.geovaneshimizu.subscription.domain.subscription

import java.time.Instant
import java.time.LocalDateTime
import java.util.*

data class NewSubscription(val purchaseOrder: PurchaseOrder) {

    data class PurchaseOrder(val id: Long,
                             val externalId: UUID,
                             val createdAt: Instant,
                             val userEmail: String,
                             val eventId: Long,
                             val sectorId: Long,
                             val seatId: Long)

    fun valuesToAdd(): AddSubscriptionValues =
            AddSubscriptionValues(
                    purchaseOrderId = this.purchaseOrder.externalId,
                    expiresAt = expiresAt(),
                    userEmail = this.purchaseOrder.userEmail,
                    sectorId = this.purchaseOrder.sectorId,
                    seatId = this.purchaseOrder.seatId)

    private fun expiresAt(): LocalDateTime {
        return SubscriptionEnd
                .byUserEmail(UserEmail(this.purchaseOrder.userEmail))
                .invoke(this.purchaseOrder.createdAt)
                .toLocalDateTime()
    }
}
