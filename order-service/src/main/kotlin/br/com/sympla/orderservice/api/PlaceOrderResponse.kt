package br.com.sympla.orderservice.api

import br.com.sympla.orderservice.domain.order.PurchaseOrder
import java.time.Instant
import java.util.UUID

data class PlaceOrderResponse(val id: UUID,
                              val createdAt: Instant,
                              val userEmail: String,
                              val eventId: Long,
                              val sectorId: Long,
                              val seatId: Long) {

    companion object {

        operator fun invoke(purchaseOrder: PurchaseOrder): PlaceOrderResponse {
            return PlaceOrderResponse(
                purchaseOrder.externalId,
                purchaseOrder.createdAt,
                purchaseOrder.userEmail,
                purchaseOrder.eventId,
                purchaseOrder.sectorId,
                purchaseOrder.seatId)
        }
    }
}
