package dev.geovaneshimizu.subscriptionservice.domain.subscription

interface Subscriptions {

    fun addSubscription(values: AddSubscriptionValues): Subscription
}
