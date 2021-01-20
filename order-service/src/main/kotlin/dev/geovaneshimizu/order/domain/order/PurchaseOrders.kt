package dev.geovaneshimizu.order.domain.order

interface PurchaseOrders {

    fun placeOrder(values: PlaceOrderValues): PurchaseOrder?
}
