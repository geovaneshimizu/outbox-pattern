package br.com.sympla.orderservice.domain

interface SubscriptionListener {

    fun onSubscribed(event: SubscriptionCreated)
}
