package dev.geovaneshimizu.order.domain

interface TransactionalRepository {

    fun <T> executeInTransactionWithResult(block: () -> T?): T?

    fun executeInTransactionWithoutResult(block: () -> Unit)
}
