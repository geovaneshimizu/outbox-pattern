package dev.geovaneshimizu.order.domain.order

import dev.geovaneshimizu.order.domain.outbox.NewMessageValues

data class PurchaseOrderCreated(val purchaseOrder: PurchaseOrder) : PurchaseOrderEvent {

    override fun messageDraft(): NewMessageValues {
        return NewMessageValues(event = this.javaClass.canonicalName, payload = this)
    }
}
