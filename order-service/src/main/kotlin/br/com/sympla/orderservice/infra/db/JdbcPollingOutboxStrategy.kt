package br.com.sympla.orderservice.infra.db

interface JdbcPollingOutboxStrategy {

    fun pull()
}
