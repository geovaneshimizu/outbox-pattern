package dev.geovaneshimizu.order.infra.db

import dev.geovaneshimizu.order.domain.outbox.NewMessageValues
import dev.geovaneshimizu.order.domain.outbox.OutboxMessage
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject

class InsertOutboxMessage(private val jdbcTemplate: JdbcTemplate,
                          private val jsonbMapper: JsonbMapper) : (NewMessageValues) -> OutboxMessage {

    companion object {
        private val insertScript: String = """
            INSERT INTO order_service.outbox(created_at, event, payload)
              VALUES (now(), ?, ?::jsonb)
              RETURNING id, created_at, event, payload
        """.trimIndent()
    }

    override fun invoke(newMessageValues: NewMessageValues): OutboxMessage {
        return this.jdbcTemplate.queryForObject(
                insertScript,
                newMessageValues.event,
                this.jsonbMapper.asJsonString(newMessageValues.payload))
        { resultSet, _ ->
            OutboxMessage(
                    id = resultSet.getLong("id"),
                    createdAt = resultSet.getInstant("created_at"),
                    event = resultSet.getString("event"),
                    payload = resultSet.getJsonb("payload", this.jsonbMapper))
        }
    }
}
