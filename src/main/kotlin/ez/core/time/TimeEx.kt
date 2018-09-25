package ez.core.time

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

fun LocalDate.toTimestamp(): Timestamp = Timestamp.valueOf(atStartOfDay())

fun LocalDateTime.toTimeStamp(): Timestamp = Timestamp.valueOf(this)