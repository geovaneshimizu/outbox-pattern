package br.com.sympla.orderservice.domain.order

interface PublishSubscriptionCreated {

    fun publish(event: SubscriptionCreated)
}
