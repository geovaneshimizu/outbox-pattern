package br.com.sympla.orderservice.domain

sealed class PurchaseOrderEvent {

    abstract fun publishEventValues(): PublishEventValues

    data class Created(val purchaseOrder: PurchaseOrder) : PurchaseOrderEvent() {

        override fun publishEventValues(): PublishEventValues {
            return PublishEventValues(event = this.javaClass.canonicalName, payload = this.purchaseOrder)
        }
    }
}
