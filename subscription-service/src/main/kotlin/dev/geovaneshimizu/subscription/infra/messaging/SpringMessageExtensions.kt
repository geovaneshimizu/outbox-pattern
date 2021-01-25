package dev.geovaneshimizu.subscription.infra.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.messaging.Message

inline fun <reified T> Message<String>.getPayloadMessageAs(objectMapper: ObjectMapper): T {
    val payload = this.payload

    return with(objectMapper) {
        this.readTree(payload)
            .let { it["Message"].asText() }
            .let { this.readTree(it) }
            .let { this.convertValue(it, T::class.java) }
    }
}
