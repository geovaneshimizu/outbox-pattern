package dev.geovaneshimizu.subscription.domain.subscription

object IdempotencyNewSubscriptionKey : (NewSubscription) -> String {

    override fun invoke(newSubscription: NewSubscription): String {
        return newSubscription.purchaseOrder.externalId.toString()
    }
}
