package dev.geovaneshimizu.order.infra.db

import dev.geovaneshimizu.order.domain.order.PlaceOrderValues
import dev.geovaneshimizu.order.domain.order.PurchaseOrder
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject

class InsertPurchaseOrder(private val jdbcTemplate: JdbcTemplate) : (PlaceOrderValues) -> PurchaseOrder {

    companion object {
        private val insertScript: String = """
            INSERT INTO order_service.purchase_order(user_email, event_id, sector_id, seat_id)
              VALUES (?, ?, ?, ?)
              RETURNING id, external_id, created_at, user_email, event_id, sector_id, seat_id
        """.trimIndent()
    }

    override fun invoke(placeOrderValues: PlaceOrderValues): PurchaseOrder {
        return this.jdbcTemplate.queryForObject(
                insertScript,
                placeOrderValues.userEmail,
                placeOrderValues.eventId,
                placeOrderValues.sectorId,
                placeOrderValues.seatId)
        { resultSet, _ ->
            PurchaseOrder(
                    id = resultSet.getLong("id"),
                    externalId = resultSet.getUUID("external_id"),
                    createdAt = resultSet.getInstant("created_at"),
                    userEmail = resultSet.getString("user_email"),
                    eventId = resultSet.getLong("event_id"),
                    sectorId = resultSet.getLong("sector_id"),
                    seatId = resultSet.getLong("seat_id"))
        }
    }
}
