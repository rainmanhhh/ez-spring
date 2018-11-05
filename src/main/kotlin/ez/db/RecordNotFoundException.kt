package ez.db

import org.jooq.DAO

class RecordNotFoundException(dao: DAO<*, *, *>, id: Any?) : RuntimeException("cannot find record in [${dao.getTable().name}] with key:$id")