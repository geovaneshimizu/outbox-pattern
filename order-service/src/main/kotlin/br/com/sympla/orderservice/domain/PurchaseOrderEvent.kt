package br.com.sympla.orderservice.domain

interface PurchaseOrderEvent {

    fun publishEventValues(): PublishEventValues
}
