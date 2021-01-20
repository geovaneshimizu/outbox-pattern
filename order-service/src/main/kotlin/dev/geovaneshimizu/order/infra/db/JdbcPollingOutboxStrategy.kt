package dev.geovaneshimizu.order.infra.db

interface JdbcPollingOutboxStrategy {

    fun pull()
}
