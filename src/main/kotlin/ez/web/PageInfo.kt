package ez.web

import io.swagger.annotations.ApiParam

/**
 * 分页查询参数
 * @property size Int 单页记录数
 * @property no Int 页码，从0开始
 * @constructor
 */
class PageInfo @JvmOverloads constructor(
    @field:ApiParam("单页记录数", defaultValue = DEFAULT_PAGE_SIZE.toString())
    var size: Int = DEFAULT_PAGE_SIZE,
    @field:ApiParam("页码，从0开始", defaultValue = DEFAULT_PAGE_NO.toString())
    var no: Int = DEFAULT_PAGE_NO
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val DEFAULT_PAGE_NO = 0
    }
}