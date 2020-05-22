package ez.web

import io.swagger.v3.oas.annotations.media.Schema

class PageInfo @JvmOverloads constructor(
  @field:Schema(title = "单页记录数", defaultValue = DEFAULT_PAGE_SIZE.toString())
  var size: Int = DEFAULT_PAGE_SIZE,
  @field:Schema(title = "页码", description = "从0开始", defaultValue = DEFAULT_PAGE_NO.toString())
  var no: Int = DEFAULT_PAGE_NO
) {
    /**
     *
     */
    companion object {
        /**
         * default page size
         */
        const val DEFAULT_PAGE_SIZE: Int = 20
        /**
         * default page number. start from 0
         */
        const val DEFAULT_PAGE_NO: Int = 0
    }
}