package ez.db

import org.jooq.*
import org.jooq.impl.DSL
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

class RecordNotFoundException(dao: DAO<*, *, *>, id: Any?) : RuntimeException("cannot find record in [${dao.getTable().name}] with key:$id")

//todo use cache
fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY> DAO<RECORD, POJO, KEY>.getOrNull(id: KEY): POJO? {
    @Suppress("UNCHECKED_CAST")
    return (this as DAO<RECORD, POJO?, KEY>).findById(id)
}

fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY> DAO<RECORD, POJO, KEY>.get(id: KEY): POJO {
    return getOrNull(id) ?: throw RecordNotFoundException(this, id)
}

fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY> DAO<RECORD, POJO, KEY>.dsl(): DSLContext = DSL.using(configuration())

fun <RECORD : UpdatableRecord<RECORD>, POJO : Any, KEY> DAO<RECORD, POJO, KEY>.page(
    pageNo: Int,
    pageSize: Int,
    queryModifier: SelectWhereStep<RECORD>.(Table<RECORD>) -> SelectLimitStep<RECORD> = { this }
): Page<POJO> {
    val context = dsl()
    val query = context.selectFrom(table).queryModifier(table)
    val count = context.fetchCount(query)
    val list: List<POJO> = query.offset(pageNo * pageSize).limit(pageSize).fetch().map(mapper())
    val request = PageRequest.of(pageNo, pageSize)
    return PageImpl(list, request, count.toLong())
}

fun <RECORD : Record, POJO : Any> DSLContext.fetchPage(query: SelectLimitStep<RECORD>, pageNo: Int, pageSize: Int, recordMapper: RecordMapper<RECORD, POJO>): Page<POJO> {
    val count = fetchCount(query)
    val list = fetch(query.offset(pageNo * pageSize).limit(pageSize)).map(recordMapper)
    val request = PageRequest.of(pageNo, pageSize)
    return PageImpl(list, request, count.toLong())
}

fun <RECORD : Record, POJO: Any> SelectLimitStep<RECORD>.page(context: DSLContext, pageNo: Int, pageSize: Int, recordMapper: RecordMapper<RECORD, POJO>): Page<POJO> =
    context.fetchPage(this, pageNo, pageSize, recordMapper)

fun <RECORD : Record, POJO : Any> Table<RECORD>.mapper(pojoClass: Class<POJO>, configuration: Configuration): RecordMapper<RECORD, POJO> {
    return configuration.recordMapperProvider().provide(recordType(), pojoClass)
}