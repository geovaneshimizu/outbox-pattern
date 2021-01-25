package dev.geovaneshimizu.subscription.domain.subscription

interface SubscriptionRepository {

    fun addSubscription(values: AddSubscriptionValues): Subscription
}
