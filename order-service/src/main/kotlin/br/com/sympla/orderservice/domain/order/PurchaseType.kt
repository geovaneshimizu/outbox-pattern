package br.com.sympla.orderservice.domain.order

enum class PurchaseType(private val id: Int) {

    SINGLE(1),

    SUBSCRIPTION(2);

    companion object {

        private val idToPurchaseType: Map<Int, PurchaseType> by lazy {
            values().associateBy { it.id }
        }

        operator fun invoke(id: Int): PurchaseType? {
            return idToPurchaseType[id]
        }
    }
}
