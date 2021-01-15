package br.com.sympla.orderservice.domain.order

interface SubscriptionListener {

    fun onSubscribed(event: SubscriptionCreated)
}
