package ez.db.dao

import ez.db.RecordNotFoundException
import ez.db.step.ezPage
import org.jooq.*
import org.springframework.data.domain.Page

fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY> DAO<RECORD, POJO, KEY>.ezFindOrNull(
    id: KEY
): POJO? = findById(id)

fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY> DAO<RECORD, POJO, KEY>.ezFind(
    id: KEY
): POJO = findById(id) ?: throw RecordNotFoundException(this, id)

fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY> DAO<RECORD, POJO, KEY>.ezPage(
    pageNo: Int,
    pageSize: Int,
    queryModifier: SelectWhereStep<RECORD>.(Table<RECORD>) -> SelectLimitStep<RECORD> = { this }
): Page<POJO> {
    val dsl = configuration().dsl()
    val query = dsl.selectFrom(table).queryModifier(table)
    return query.ezPage(dsl, pageNo, pageSize, mapper())
}