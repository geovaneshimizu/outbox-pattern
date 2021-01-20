package dev.geovaneshimizu.order.domain.order

interface SubscriptionListener {

    fun onSubscribed(event: SubscriptionCreated)
}
