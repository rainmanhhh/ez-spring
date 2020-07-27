package ez.web

import io.swagger.v3.oas.annotations.Parameter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class DateTimeInfo(
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Parameter(description = "start time(pattern: yyyy-MM-dd HH:mm:ss)")
  var start: LocalDateTime,
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Parameter(description = "end time(pattern: yyyy-MM-dd HH:mm:ss) ")
  var end: LocalDateTime
)