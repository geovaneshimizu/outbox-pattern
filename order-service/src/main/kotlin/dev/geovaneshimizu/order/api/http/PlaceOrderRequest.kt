package dev.geovaneshimizu.order.api.http

import dev.geovaneshimizu.order.domain.order.PlaceOrderValues
import dev.geovaneshimizu.order.domain.order.PurchaseType

data class PlaceOrderRequest(val userEmail: String,
                             val eventId: Long,
                             val sectorId: Long,
                             val seatId: Long,
                             val purchaseType: Int) {

    fun toPlaceOrderValues(): PlaceOrderValues {
        return PlaceOrderValues(
                userEmail = this.userEmail,
                eventId = this.eventId,
                sectorId = this.sectorId,
                seatId = this.seatId,
                purchaseType = PurchaseType(this.purchaseType)
                        ?: throw IllegalArgumentException("No purchase type with id=${this.purchaseType}"))
    }
}
