package dev.geovaneshimizu.subscription.domain.subscription

interface NewSubscriptionListener<T> {

    fun listen(message: T)
}
