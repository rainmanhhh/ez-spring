package ez.core.time

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

/**
 *
 * @receiver LocalDate
 * @return Timestamp
 */
fun LocalDate.toTimestamp(): Timestamp = Timestamp.valueOf(atStartOfDay())

/**
 *
 * @receiver LocalDateTime
 * @return Timestamp
 */
fun LocalDateTime.toTimeStamp(): Timestamp = Timestamp.valueOf(this)