package br.com.sympla.orderservice.domain

data class PlaceOrderValues(val userEmail: String,
                            val eventId: Long,
                            val sectorId: Long,
                            val seatId: Long,
                            val purchaseType: PurchaseType) {

    fun associatedEvents(): List<(PurchaseOrder) -> PurchaseOrderEvent> {
        val createdEvent: (PurchaseOrder) -> PurchaseOrderEvent = { PurchaseOrderCreated(it) }

        return when (this.purchaseType) {
            PurchaseType.SINGLE -> listOf(createdEvent)

            PurchaseType.SUBSCRIPTION -> {
                val subscriptionEvent: (PurchaseOrder) -> PurchaseOrderEvent = { SubscriptionCreated(it) }
                listOf(createdEvent, subscriptionEvent)
            }
        }
    }
}
