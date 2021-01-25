package dev.geovaneshimizu.subscription.domain.subscription

interface SubscriptionRepository {

    fun insert(values: AddSubscriptionValues): Subscription
}
