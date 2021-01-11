package br.com.sympla.orderservice.domain

interface PurchaseOrderListener {

    fun onCreated(event: PurchaseOrderCreated)
}
