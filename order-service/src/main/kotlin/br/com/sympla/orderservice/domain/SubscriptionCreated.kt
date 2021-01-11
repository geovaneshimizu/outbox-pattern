package br.com.sympla.orderservice.domain

data class SubscriptionCreated(val purchaseOrder: PurchaseOrder) : PurchaseOrderEvent {

    override fun publishEventValues(): PublishEventValues {
        return PublishEventValues(event = this.javaClass.canonicalName, payload = this)
    }
}
