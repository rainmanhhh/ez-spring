package ez.web

import ez.web.PageInfo.Companion.DEFAULT_PAGE_NO
import ez.web.PageInfo.Companion.DEFAULT_PAGE_SIZE
import io.swagger.annotations.ApiParam

/**
 * pagination params
 * @property size Int page size. default value is [DEFAULT_PAGE_SIZE]
 * @property no Int page number. start from 0. default value is [DEFAULT_PAGE_NO]
 * @constructor
 */
class PageInfo @JvmOverloads constructor(
    @field:ApiParam("单页记录数", defaultValue = DEFAULT_PAGE_SIZE.toString())
    var size: Int = DEFAULT_PAGE_SIZE,
    @field:ApiParam("页码，从0开始", defaultValue = DEFAULT_PAGE_NO.toString())
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