package br.com.sympla.orderservice.domain.order

import br.com.sympla.orderservice.domain.outbox.NewMessageValues

interface PurchaseOrderEvent {

    fun messageDraft(): NewMessageValues
}
