package br.com.sympla.orderservice.infra

interface JdbcConsumeOutboxStrategy {

    fun consume()
}
