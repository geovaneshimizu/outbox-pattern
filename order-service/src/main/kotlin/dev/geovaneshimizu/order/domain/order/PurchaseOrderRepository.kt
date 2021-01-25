package dev.geovaneshimizu.order.domain.order

import dev.geovaneshimizu.order.domain.TransactionalRepository

interface PurchaseOrderRepository : TransactionalRepository {

    fun insert(values: PlaceOrderValues): PurchaseOrder
}
