package dev.geovaneshimizu.order.domain.order

interface PurchaseOrderListener {

    fun onCreated(event: PurchaseOrderCreated)
}
