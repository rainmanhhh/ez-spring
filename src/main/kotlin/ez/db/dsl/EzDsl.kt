package ez.db.dsl

import org.jooq.Condition
import org.jooq.impl.DSL

/**
 * contains util functions for jooq [DSL]
 */
object EzDsl {
    /**
     *
     * @param conditions Array<out Condition?>
     * @return Condition null if all elements of [conditions] is null; else and(...conditions)
     */
    fun and(vararg conditions: Condition?): Condition? = conditions.filterNotNull().let {
        if (it.isEmpty()) null
        else DSL.and(it)
    }

    /**
     *
     * @param conditions Array<out Condition?>
     * @return Condition null if all elements of [conditions] is null; else or(...conditions)
     */
    fun or(vararg conditions: Condition?): Condition? = conditions.filterNotNull().let {
        if (it.isEmpty()) null
        else DSL.or(it)
    }
}