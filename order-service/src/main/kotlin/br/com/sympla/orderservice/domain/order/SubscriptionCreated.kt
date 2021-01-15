package br.com.sympla.orderservice.domain.order

import br.com.sympla.orderservice.domain.outbox.NewMessageValues

data class SubscriptionCreated(val purchaseOrder: PurchaseOrder) : PurchaseOrderEvent {

    override fun messageDraft(): NewMessageValues {
        return NewMessageValues(event = this.javaClass.canonicalName, payload = this)
    }
}
