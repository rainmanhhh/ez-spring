package ez.db.table

import org.jooq.Configuration
import org.jooq.Record
import org.jooq.RecordMapper
import org.jooq.Table

fun <RECORD : Record, POJO : Any> Table<RECORD>.mapper(
    pojoClass: Class<POJO>, configuration: Configuration
): RecordMapper<RECORD, POJO> = configuration.recordMapperProvider().provide(recordType(), pojoClass)