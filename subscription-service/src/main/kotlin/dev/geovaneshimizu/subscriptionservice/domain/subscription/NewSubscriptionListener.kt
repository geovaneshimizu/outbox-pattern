package dev.geovaneshimizu.subscriptionservice.domain.subscription

interface NewSubscriptionListener<T> {

    fun listen(message: T)
}
