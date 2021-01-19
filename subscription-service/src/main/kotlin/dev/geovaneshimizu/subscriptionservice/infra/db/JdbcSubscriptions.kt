package dev.geovaneshimizu.subscriptionservice.infra.db

import dev.geovaneshimizu.subscriptionservice.domain.subscription.AddSubscriptionValues
import dev.geovaneshimizu.subscriptionservice.domain.subscription.Subscription
import dev.geovaneshimizu.subscriptionservice.domain.subscription.Subscriptions
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class JdbcSubscriptions(private val jdbcTemplate: JdbcTemplate) : Subscriptions {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    override fun addSubscription(values: AddSubscriptionValues): Subscription {
        return InsertSubscription(this.jdbcTemplate, values)()
                .also { logger.info { "Inserted $it" } }
    }
}
