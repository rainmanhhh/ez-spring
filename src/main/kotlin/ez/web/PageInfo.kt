package ez.web

import io.swagger.annotations.ApiParam

class PageInfo @JvmOverloads constructor(
    @field:ApiParam("页码", defaultValue = DEFAULT_PAGE_NO.toString())
    var no: Int = DEFAULT_PAGE_NO,
    @field:ApiParam("页面大小", defaultValue = DEFAULT_PAGE_SIZE.toString())
    var size: Int = DEFAULT_PAGE_SIZE
) {
    companion object {
        const val DEFAULT_PAGE_NO = 0
        const val DEFAULT_PAGE_SIZE = 20
    }
}