package dev.geovaneshimizu.order.domain.order

interface PublishSubscriptionCreated {

    fun publish(event: SubscriptionCreated)
}
