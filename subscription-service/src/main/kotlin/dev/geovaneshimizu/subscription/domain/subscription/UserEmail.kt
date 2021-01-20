package dev.geovaneshimizu.subscription.domain.subscription

sealed class UserEmail {

    companion object {
        operator fun invoke(emailAddress: String): UserEmail {
            return if (emailAddress.endsWith("@partner.com")) {
                Partner
            } else {
                Generic
            }
        }
    }

    object Partner : UserEmail()

    object Generic : UserEmail()
}
