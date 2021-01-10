package br.com.sympla.orderservice.web

import br.com.sympla.orderservice.domain.PurchaseOrders
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/orders")
class PlaceOrderEndpoint(private val purchaseOrders: PurchaseOrders) {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @PostMapping
    fun post(@RequestBody placeOrderRequest: PlaceOrderRequest): ResponseEntity<PlaceOrderResponse> {
        logger.info { "Received $placeOrderRequest" }

        val purchaseOrder = this.purchaseOrders.placeOrder(placeOrderRequest.asCommand())

        return purchaseOrder?.let {
            ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequestUri()
                            .path("/")
                            .path(purchaseOrder.publicId.toString())
                            .build()
                            .toUri())
                    .body(PlaceOrderResponse(purchaseOrder))
        } ?: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
}
