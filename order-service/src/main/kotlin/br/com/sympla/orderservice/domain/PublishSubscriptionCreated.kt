package br.com.sympla.orderservice.domain

interface PublishSubscriptionCreated {

    fun publish(event: SubscriptionCreated)
}
