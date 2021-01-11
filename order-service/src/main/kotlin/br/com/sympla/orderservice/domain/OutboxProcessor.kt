package br.com.sympla.orderservice.domain

interface OutboxProcessor {

    fun consumeOutbox()
}
