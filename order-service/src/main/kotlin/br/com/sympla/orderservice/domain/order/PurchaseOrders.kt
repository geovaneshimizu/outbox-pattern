package br.com.sympla.orderservice.domain.order

interface PurchaseOrders {

    fun placeOrder(values: PlaceOrderValues): PurchaseOrder?
}
