package dev.geovaneshimizu.subscription.infra.db

import dev.geovaneshimizu.subscription.domain.subscription.AddSubscriptionValues
import dev.geovaneshimizu.subscription.domain.subscription.Subscription
import dev.geovaneshimizu.subscription.domain.subscription.SubscriptionRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class JdbcSubscriptionRepository(private val jdbcTemplate: JdbcTemplate) : SubscriptionRepository {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    override fun insert(values: AddSubscriptionValues): Subscription {
        logger.info { "Adding $values" }
        return InsertSubscription(this.jdbcTemplate, values)()
    }
}
