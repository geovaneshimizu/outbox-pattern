package br.com.sympla.orderservice.infra

import br.com.sympla.orderservice.domain.PlaceOrderValues
import br.com.sympla.orderservice.domain.PurchaseOrder
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject

class InsertPurchaseOrder(private val jdbcTemplate: JdbcTemplate,
                          private val placeOrderValues: PlaceOrderValues) : () -> PurchaseOrder {

    companion object {
        private val insertScript: String = """
            INSERT INTO order_service.purchase_order(public_id, created_at, user_email, event_id, sector_id, seat_id)
              VALUES (uuid_generate_v4(), now(), ?, ?, ?, ?)
              RETURNING id, public_id, created_at, user_email, event_id, sector_id, seat_id
        """.trimIndent()
    }

    override fun invoke(): PurchaseOrder {
        return this.jdbcTemplate.queryForObject(
                insertScript,
                this.placeOrderValues.userEmail,
                this.placeOrderValues.eventId,
                this.placeOrderValues.sectorId,
                this.placeOrderValues.seatId)
        { resultSet, _ ->
            PurchaseOrder(
                    id = resultSet.getLong("id"),
                    publicId = resultSet.getUUID("public_id"),
                    createdAt = resultSet.getInstant("created_at"),
                    userEmail = resultSet.getString("user_email"),
                    eventId = resultSet.getLong("event_id"),
                    sectorId = resultSet.getLong("sector_id"),
                    seatId = resultSet.getLong("seat_id"))
        }
    }
}
