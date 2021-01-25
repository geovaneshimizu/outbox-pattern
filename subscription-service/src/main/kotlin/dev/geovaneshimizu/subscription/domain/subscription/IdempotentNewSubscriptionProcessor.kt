package dev.geovaneshimizu.subscription.domain.subscription

interface IdempotentNewSubscriptionProcessor<T> {

    fun acceptIfNotExists(message: T, consumer: (NewSubscription) -> Unit): NewSubscription
}
