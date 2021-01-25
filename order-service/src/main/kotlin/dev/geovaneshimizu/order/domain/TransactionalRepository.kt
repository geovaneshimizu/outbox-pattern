package dev.geovaneshimizu.order.domain

interface TransactionalRepository {

    fun <T> executeInTransaction(block: () -> T?): T?
}
