package ez.web

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema

class PageInfo @JvmOverloads constructor(
  @Schema(defaultValue = "20")
  @Parameter(description = "单页记录数")
  var size: Int = 20,
  @Schema(defaultValue = "0")
  @Parameter(description = "页码，从0开始")
  var no: Int = 0
)