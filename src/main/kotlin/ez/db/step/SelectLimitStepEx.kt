package ez.db.step

import ez.db.dsl.ezPage
import ez.db.table.mapper
import ez.web.PageInfo
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.SelectLimitStep
import org.jooq.impl.DSL
import org.springframework.data.domain.Page

/**
 *
 * @receiver SelectLimitStep<RECORD>
 * @param context DSLContext
 * @param pageNo Int start from 0
 * @param pageSize Int
 * @param recordMapper RecordMapper<RECORD, POJO>
 * @return Page<POJO>
 */
fun <RECORD : Record, POJO : Any> SelectLimitStep<RECORD>.ezPage(
    context: DSLContext, pageNo: Int, pageSize: Int, recordMapper: RecordMapper<RECORD, POJO>
): Page<POJO> = context.ezPage(this, pageNo, pageSize, recordMapper)

/**
 *
 * @receiver SelectLimitStep<RECORD>
 * @param pageNo Int start from 0
 * @param pageSize Int
 * @param recordMapper RecordMapper<RECORD, POJO>
 * @return Page<POJO>
 */
fun <RECORD : Record, POJO : Any> SelectLimitStep<RECORD>.ezPage(
    pageNo: Int, pageSize: Int, recordMapper: RecordMapper<RECORD, POJO>
): Page<POJO> = ezPage(DSL.using(configuration()), pageNo, pageSize, recordMapper)

/**
 *
 * @receiver SelectLimitStep<RECORD>
 * @param pageNo Int start from 0
 * @param pageSize Int
 * @param pojoClass Class<POJO>
 * @return Page<POJO>
 */
fun <RECORD : Record, POJO : Any> SelectLimitStep<RECORD>.ezPage(
    pageNo: Int, pageSize: Int, pojoClass: Class<POJO>
): Page<POJO> {
    val mapper = asTable().mapper(pojoClass, configuration())
    return ezPage(DSL.using(configuration()), pageNo, pageSize, mapper)
}

/**
 *
 * @receiver SelectLimitStep<RECORD>
 * @param context DSLContext
 * @param pageInfo PageInfo
 * @param recordMapper RecordMapper<RECORD, POJO>
 * @return Page<POJO>
 */
fun <RECORD : Record, POJO : Any> SelectLimitStep<RECORD>.ezPage(
    context: DSLContext, pageInfo: PageInfo, recordMapper: RecordMapper<RECORD, POJO>
): Page<POJO> = context.ezPage(this, pageInfo.no, pageInfo.size, recordMapper)

/**
 *
 * @receiver SelectLimitStep<RECORD>
 * @param pageInfo PageInfo
 * @param recordMapper RecordMapper<RECORD, POJO>
 * @return Page<POJO>
 */
fun <RECORD : Record, POJO : Any> SelectLimitStep<RECORD>.ezPage(
    pageInfo: PageInfo, recordMapper: RecordMapper<RECORD, POJO>
): Page<POJO> = ezPage(DSL.using(configuration()), pageInfo, recordMapper)

/**
 *
 * @receiver SelectLimitStep<RECORD>
 * @param pageInfo PageInfo
 * @param pojoClass Class<POJO>
 * @return Page<POJO>
 */
fun <RECORD : Record, POJO : Any> SelectLimitStep<RECORD>.ezPage(
    pageInfo: PageInfo, pojoClass: Class<POJO>
): Page<POJO> {
    val mapper = asTable().mapper(pojoClass, configuration())
    return ezPage(DSL.using(configuration()), pageInfo, mapper)
}