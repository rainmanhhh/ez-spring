package ez.db.dao

import ez.db.RecordNotFoundException
import ez.db.step.ezPage
import ez.web.PageInfo
import org.jooq.*
import org.springframework.data.domain.Page

/**
 *
 * @receiver DAO<RECORD, POJO, KEY>
 * @param id KEY
 * @return POJO?
 */
fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY>
    DAO<RECORD, POJO, KEY>.ezFindOrNull(id: KEY): POJO? = findById(id)

/**
 *
 * @receiver DAO<RECORD, POJO, KEY>
 * @param id KEY
 * @return POJO
 */
fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY>
    DAO<RECORD, POJO, KEY>.ezFind(id: KEY): POJO =
    findById(id) ?: throw RecordNotFoundException(this, id)

/**
 *
 * @receiver DAO<RECORD, POJO, KEY>
 * @param pageNo Int start from 0
 * @param pageSize Int
 * @param queryModifier SelectWhereStep<RECORD>.(Table<RECORD>) -> SelectLimitStep<RECORD>
 * @return Page<POJO>
 */
fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY> DAO<RECORD, POJO, KEY>.ezPage(
    pageNo: Int,
    pageSize: Int,
    queryModifier: SelectWhereStep<RECORD>.(Table<RECORD>) -> SelectLimitStep<RECORD> = { this }
): Page<POJO> {
    val dsl = configuration().dsl()
    val query = dsl.selectFrom(table).queryModifier(table)
    return query.ezPage(dsl, pageNo, pageSize, mapper())
}

/**
 *
 * @receiver DAO<RECORD, POJO, KEY>
 * @param pageInfo PageInfo
 * @param queryModifier SelectWhereStep<RECORD>.(Table<RECORD>) -> SelectLimitStep<RECORD>
 * @return Page<POJO>
 */
fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY> DAO<RECORD, POJO, KEY>.ezPage(
    pageInfo: PageInfo,
    queryModifier: SelectWhereStep<RECORD>.(Table<RECORD>) -> SelectLimitStep<RECORD> = { this }
): Page<POJO> = ezPage(pageInfo.no, pageInfo.size, queryModifier)