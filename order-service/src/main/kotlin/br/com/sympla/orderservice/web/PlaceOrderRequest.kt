package br.com.sympla.orderservice.web

import br.com.sympla.orderservice.domain.PlaceOrderValues

data class PlaceOrderRequest(val userEmail: String,
                             val eventId: Long,
                             val sectorId: Long,
                             val seatId: Long) {

    fun toPlaceOrderValues(): PlaceOrderValues {
        return PlaceOrderValues(
                userEmail = this.userEmail,
                eventId = this.eventId,
                sectorId = this.sectorId,
                seatId = this.seatId)
    }
}
