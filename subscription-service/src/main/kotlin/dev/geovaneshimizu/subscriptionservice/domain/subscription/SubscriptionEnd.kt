package dev.geovaneshimizu.subscriptionservice.domain.subscription

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

sealed class SubscriptionEnd(private val purchaseInstant: Instant) {

    companion object {

        fun byUserEmail(userEmail: UserEmail): (Instant) -> SubscriptionEnd {
            return if (userEmail is UserEmail.Partner) {
                { begin: Instant -> OneYear(begin) }
            } else {
                { begin: Instant -> SixMonths(begin) }
            }
        }
    }

    fun toLocalDateTime(): LocalDateTime {
        return calculate(LocalDateTime.ofInstant(this.purchaseInstant, ZoneOffset.UTC))
    }

    abstract fun calculate(begin: LocalDateTime): LocalDateTime

    class OneYear(purchaseInstant: Instant) : SubscriptionEnd(purchaseInstant) {

        override fun calculate(begin: LocalDateTime): LocalDateTime {
            return begin.plus(1L, ChronoUnit.YEARS)
        }
    }

    class SixMonths(purchaseInstant: Instant) : SubscriptionEnd(purchaseInstant) {

        override fun calculate(begin: LocalDateTime): LocalDateTime {
            return begin.plus(6L, ChronoUnit.MONTHS)
        }
    }
}
