package ez.db.dsl

import org.jooq.Condition
import org.jooq.impl.DSL

object EzDsl {
    fun and(vararg conditions: Condition?): Condition = DSL.and(conditions.filterNotNull())

    fun or(vararg conditions: Condition?): Condition = DSL.or(conditions.filterNotNull())
}