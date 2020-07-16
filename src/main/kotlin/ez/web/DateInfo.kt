package ez.web

import io.swagger.v3.oas.annotations.Parameter
import java.time.LocalDate

class DateInfo(
  @Parameter(description = "开始日期")
  var start: LocalDate,
  @Parameter(description = "结束日期")
  var end: LocalDate
)