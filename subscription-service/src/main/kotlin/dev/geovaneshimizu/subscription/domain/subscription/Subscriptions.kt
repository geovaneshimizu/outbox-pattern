package dev.geovaneshimizu.subscription.domain.subscription

interface Subscriptions {

    fun addSubscription(values: AddSubscriptionValues): Subscription
}
