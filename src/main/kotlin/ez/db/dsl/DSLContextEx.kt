package ez.db.dsl

import ez.web.PageInfo
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.SelectLimitStep
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

/**
 *
 * @receiver DSLContext
 * @param query SelectLimitStep<RECORD>
 * @param pageNo Int
 * @param pageSize Int
 * @param recordMapper RecordMapper<RECORD, POJO>
 * @return Page<POJO>
 */
fun <RECORD : Record, POJO : Any> DSLContext.ezPage(
    query: SelectLimitStep<RECORD>, pageNo: Int, pageSize: Int,
    recordMapper: RecordMapper<RECORD, POJO>
): Page<POJO> {
    val count = fetchCount(query)
    val list = fetch(query.offset(pageNo * pageSize).limit(pageSize)).map(recordMapper)
    val request = PageRequest.of(pageNo, pageSize)
    return PageImpl(list, request, count.toLong())
}

/**
 *
 * @receiver DSLContext
 * @param query SelectLimitStep<RECORD>
 * @param pageInfo PageInfo
 * @param recordMapper RecordMapper<RECORD, POJO>
 * @return Page<POJO>
 */
fun <RECORD : Record, POJO : Any> DSLContext.ezPage(
    query: SelectLimitStep<RECORD>, pageInfo: PageInfo,
    recordMapper: RecordMapper<RECORD, POJO>
): Page<POJO> = ezPage(query, pageInfo.no, pageInfo.size, recordMapper)