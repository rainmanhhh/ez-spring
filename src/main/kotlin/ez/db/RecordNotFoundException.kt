package ez.db

import org.jooq.DAO

/**
 * cannot find record in table with provided key
 * @constructor
 */
class RecordNotFoundException(dao: DAO<*, *, *>, id: Any?) :
    RuntimeException("cannot find record in [${dao.getTable().name}] with key:$id")