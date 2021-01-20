package dev.geovaneshimizu.order.domain.order

import dev.geovaneshimizu.order.domain.outbox.NewMessageValues

interface PurchaseOrderEvent {

    fun messageDraft(): NewMessageValues
}
