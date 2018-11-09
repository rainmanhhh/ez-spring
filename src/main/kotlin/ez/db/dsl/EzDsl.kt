package ez.db.dsl

import org.jooq.Condition
import org.jooq.impl.DSL

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

    /**
     *
     * @param expr Any? check expr like javascript `!!`
     * @return Boolean false if expr is `null` or "" or `false`; otherwise true
     */
    fun checkAsJs(expr: Any?): Boolean = expr != null && expr != "" && expr != false

    /**
     * if [checkAsJs] ([expr]) returns true, use [provider] to create [Condition]; else return null
     * @param expr E?
     * @param provider (E) -> Condition
     * @return Condition?
     */
    fun <E : Any> condition(expr: E?, provider: (E) -> Condition) = if (EzDsl.checkAsJs(expr)) provider(expr!!) else null
}