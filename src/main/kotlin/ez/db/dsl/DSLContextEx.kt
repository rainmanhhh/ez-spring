package ez.db.dsl

import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.SelectLimitStep
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

fun <RECORD : Record, POJO : Any> DSLContext.ezPage(
    query: SelectLimitStep<RECORD>, pageNo: Int, pageSize: Int,
    recordMapper: RecordMapper<RECORD, POJO>
): Page<POJO> {
    val count = fetchCount(query)
    val list = fetch(query.offset(pageNo * pageSize).limit(pageSize)).map(recordMapper)
    val request = PageRequest.of(pageNo, pageSize)
    return PageImpl(list, request, count.toLong())
}