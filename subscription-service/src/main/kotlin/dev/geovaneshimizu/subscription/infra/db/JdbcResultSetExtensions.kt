package dev.geovaneshimizu.subscription.infra.db

import java.sql.ResultSet
import java.time.Instant
import java.util.*

fun ResultSet.getUUID(columnLabel: String): UUID {
    return this.getString(columnLabel)
            .let { UUID.fromString(it) }
}

fun ResultSet.getInstant(columnLabel: String): Instant {
    return this.getTimestamp(columnLabel, UtcTimeZone.asCalendar)
            .toInstant()
}

object UtcTimeZone {

    val asCalendar: Calendar by lazy {
        Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    }
}
