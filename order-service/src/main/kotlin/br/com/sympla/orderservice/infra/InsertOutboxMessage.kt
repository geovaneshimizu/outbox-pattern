package br.com.sympla.orderservice.infra

import br.com.sympla.orderservice.domain.OutboxMessage
import br.com.sympla.orderservice.domain.PublishEventValues
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject

class InsertOutboxMessage(private val jdbcTemplate: JdbcTemplate,
                          private val jsonbMapper: JsonbMapper,
                          private val publishEventValues: PublishEventValues) : () -> OutboxMessage {

    companion object {
        private val insertScript: String = """
            INSERT INTO order_service.outbox(created_at, event, payload)
              VALUES (now(), ?, ?::jsonb)
              RETURNING id, created_at, event, payload
        """.trimIndent()
    }

    override fun invoke(): OutboxMessage {
        return this.jdbcTemplate.queryForObject(
                insertScript,
                this.publishEventValues.event,
                this.jsonbMapper.asJsonString(this.publishEventValues.payload))
        { resultSet, _ ->
            OutboxMessage(
                    id = resultSet.getLong("id"),
                    createdAt = resultSet.getInstant("created_at"),
                    event = resultSet.getString("event"),
                    payload = resultSet.getJsonb("payload", this.jsonbMapper))
        }
    }
}
