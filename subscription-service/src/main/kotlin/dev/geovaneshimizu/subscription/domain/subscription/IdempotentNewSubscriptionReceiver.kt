package dev.geovaneshimizu.subscription.domain.subscription

interface IdempotentNewSubscriptionReceiver<T> {

    fun acceptNewSubscription(message: T, consumer: (NewSubscription) -> Unit): NewSubscription?
}
