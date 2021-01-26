package dev.geovaneshimizu.order.infra.db

import dev.geovaneshimizu.order.domain.outbox.OutboxMessage
import mu.KotlinLogging
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query

class DeleteOutboxMessages(private val jdbcTemplate: JdbcTemplate,
                           private val jsonbMapper: JsonbMapper) : (Int) -> List<OutboxMessage> {

    companion object {
        private val logger = KotlinLogging.logger { }

        private val deleteScript: String = """
            DELETE FROM order_service.outbox 
              WHERE id IN (
                SELECT id
                  FROM order_service.outbox
                  ORDER BY id
                  FOR UPDATE SKIP LOCKED
                  LIMIT ?
              )
              RETURNING id, created_at, event, payload
        """.trimIndent()
    }

    override fun invoke(limit: Int): List<OutboxMessage> {
        return try {
            this.jdbcTemplate.query(deleteScript, limit) { resultSet, _ ->
                OutboxMessage(
                        id = resultSet.getLong("id"),
                        createdAt = resultSet.getInstant("created_at"),
                        event = resultSet.getString("event"),
                        payload = resultSet.getJsonb("payload", this.jsonbMapper))
            }
        } catch (e: DataRetrievalFailureException) {
            logger.warn { "No outbox message retrieved due: $e" }
            emptyList()
        }
    }
}
