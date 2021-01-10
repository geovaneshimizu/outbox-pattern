package br.com.sympla.orderservice.domain

data class PlaceOrderValues(val userEmail: String,
                            val eventId: Long,
                            val sectorId: Long,
                            val seatId: Long) {

    fun events(): List<(PurchaseOrder) -> PurchaseOrderEvent> {
        val createdEvent: (PurchaseOrder) -> PurchaseOrderEvent = { PurchaseOrderEvent.Created(it) }

        return listOf(createdEvent)
    }
}
