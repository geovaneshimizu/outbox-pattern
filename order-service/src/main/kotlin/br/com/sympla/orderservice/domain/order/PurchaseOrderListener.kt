package br.com.sympla.orderservice.domain.order

interface PurchaseOrderListener {

    fun onCreated(event: PurchaseOrderCreated)
}
