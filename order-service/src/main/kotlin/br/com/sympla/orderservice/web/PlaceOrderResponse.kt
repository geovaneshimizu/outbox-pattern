package br.com.sympla.orderservice.web

import br.com.sympla.orderservice.domain.PurchaseOrder
import java.time.Instant
import java.util.*

data class PlaceOrderResponse(val _id: UUID,
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
