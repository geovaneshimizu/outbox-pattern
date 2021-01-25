package dev.geovaneshimizu.order.infra.db

import dev.geovaneshimizu.order.domain.outbox.NewMessageValues
import dev.geovaneshimizu.order.domain.outbox.OutboxMessage
import dev.geovaneshimizu.order.domain.outbox.OutboxRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate

class JdbcOutboxRepository(private val jdbcTemplate: JdbcTemplate,
                           private val jsonbMapper: JsonbMapper) : OutboxRepository {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    override fun insert(values: NewMessageValues): OutboxMessage {
        return InsertOutboxMessage(this.jdbcTemplate, this.jsonbMapper, values)()
    }
}
