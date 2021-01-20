package dev.geovaneshimizu.order.infra.db

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class JsonbMapper(private val objectMapper: ObjectMapper) {

    fun asJsonString(obj: Any): String {
        return this.objectMapper.writeValueAsString(obj)
    }

    fun <T> fromJsonString(json: String, clazz: Class<T>): T {
        return this.objectMapper.readValue(json, clazz)
    }

    fun <T> fromAny(obj: Any, clazz: Class<T>): T {
        return this.objectMapper.convertValue(obj, clazz)
    }
}
