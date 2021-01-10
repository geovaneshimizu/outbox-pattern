package br.com.sympla.orderservice.domain

interface PurchaseOrders {

    fun placeOrder(values: PlaceOrderValues): PurchaseOrder?
}
