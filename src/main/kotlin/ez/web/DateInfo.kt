package ez.web

import io.swagger.v3.oas.annotations.Parameter
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

class DateInfo(
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Parameter(description = "start date, pattern: yyyy-MM-dd")
  var start: LocalDate,
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Parameter(description = "end date, pattern: yyyy-MM-dd")
  var end: LocalDate
)