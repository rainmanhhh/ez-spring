package ez.db.step

import ez.db.dsl.ezPage
import ez.db.table.mapper
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.SelectLimitStep
import org.jooq.impl.DSL
import org.springframework.data.domain.Page

fun <RECORD : Record, POJO : Any> SelectLimitStep<RECORD>.ezPage(
    context: DSLContext, pageNo: Int, pageSize: Int, recordMapper: RecordMapper<RECORD, POJO>
): Page<POJO> = context.ezPage(this, pageNo, pageSize, recordMapper)

fun <RECORD : Record, POJO : Any> SelectLimitStep<RECORD>.ezPage(
    pageNo: Int, pageSize: Int, recordMapper: RecordMapper<RECORD, POJO>
): Page<POJO> = ezPage(DSL.using(configuration()), pageNo, pageSize, recordMapper)

fun <RECORD : Record, POJO : Any> SelectLimitStep<RECORD>.ezPage(
    pageNo: Int, pageSize: Int, pojoClass: Class<POJO>
): Page<POJO> {
    val mapper = asTable().mapper(pojoClass, configuration())
    return ezPage(DSL.using(configuration()), pageNo, pageSize, mapper)
}
