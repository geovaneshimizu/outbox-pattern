package dev.geovaneshimizu.subscriptionservice.infra.db

import dev.geovaneshimizu.subscriptionservice.domain.subscription.AddSubscriptionValues
import dev.geovaneshimizu.subscriptionservice.domain.subscription.Subscription
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject


class InsertSubscription(private val jdbcTemplate: JdbcTemplate,
                         private val addSubscriptionValues: AddSubscriptionValues) : () -> Subscription {

    companion object {
        private val insertScript: String = """
            INSERT INTO subscription_service.subscription(purchase_order_id, expires_at, user_email, sector_id, seat_id)
              VALUES (?, ?, ?, ?, ?)
              RETURNING id, created_at, purchase_order_id, expires_at, user_email, sector_id, seat_id
        """.trimIndent()
    }

    override fun invoke(): Subscription {
        return this.jdbcTemplate.queryForObject(
                insertScript,
                this.addSubscriptionValues.purchaseOrderId,
                this.addSubscriptionValues.expiresAt,
                this.addSubscriptionValues.userEmail,
                this.addSubscriptionValues.sectorId,
                this.addSubscriptionValues.seatId)
        { resultSet, _ ->
            Subscription(
                    id = resultSet.getLong("id"),
                    createdAt = resultSet.getInstant("created_at"),
                    purchaseOrderId = resultSet.getUUID("purchase_order_id"),
                    expiresAt = resultSet.getInstant("expires_at"),
                    userEmail = resultSet.getString("user_email"),
                    sectorId = resultSet.getLong("sector_id"),
                    seatId = resultSet.getLong("seat_id"))
        }
    }
}
