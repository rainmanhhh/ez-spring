package ez.db.table

import org.jooq.Configuration
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.Table

/**
 *
 * @receiver Table<RECORD>
 * @param pojoClass Class<POJO>
 * @param configuration Configuration
 * @return RecordMapper<RECORD, POJO>
 */
fun <RECORD : Record, POJO : Any> Table<RECORD>.mapper(
    pojoClass: Class<POJO>, configuration: Configuration
): RecordMapper<RECORD, POJO> = configuration.recordMapperProvider().provide(recordType(), pojoClass)