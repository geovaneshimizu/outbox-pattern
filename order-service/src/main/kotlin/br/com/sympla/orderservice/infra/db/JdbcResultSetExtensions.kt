package br.com.sympla.orderservice.infra.db

import java.sql.ResultSet
import java.time.Instant
import java.util.Calendar
import java.util.TimeZone
import java.util.UUID

fun ResultSet.getUUID(columnLabel: String): UUID {
    return this.getString(columnLabel)
        .let { UUID.fromString(it) }
}

fun ResultSet.getInstant(columnLabel: String): Instant {
    return this.getTimestamp(columnLabel, UtcTimeZone.asCalendar)
        .time
        .let { Instant.ofEpochMilli(it) }
}

inline fun <reified T> ResultSet.getJsonb(columnLabel: String, jsonbMapper: JsonbMapper): T {
    return this.getString(columnLabel)
        .let { jsonbMapper.fromJsonString(it, T::class.java) }
}

object UtcTimeZone {

    val asCalendar: Calendar by lazy {
        Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    }
}
