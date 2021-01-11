package br.com.sympla.orderservice.domain

data class PlaceOrderValues(val userEmail: String,
                            val eventId: Long,
                            val sectorId: Long,
                            val seatId: Long) {

    fun associatedEvents(): List<(PurchaseOrder) -> PurchaseOrderEvent> {
        val createdEvent: (PurchaseOrder) -> PurchaseOrderEvent = { PurchaseOrderCreated(it) }

        return listOf(createdEvent)
    }
}
