package ez.db.condition

import ez.db.dsl.EzDsl
import org.jooq.Condition
import org.jooq.Field
import org.jooq.impl.DSL

fun <E : Any, V> ezIf(expr: E?, elseValue: V, provider: (E) -> V): V =
    if (EzDsl.checkAsJs(expr)) provider(expr!!) else elseValue

fun <E : Any> ezCond(expr: E?, provider: (E) -> Condition) = ezIf(expr, null, provider)

fun <E : Any> ezEq(expr: E?, field: Field<E>) = ezCond(expr, field::eq)

fun ezContains(expr: String?, field: Field<String>) = ezCond(expr) { field.like("%$it%") }

fun ezStartWith(expr: String?, field: Field<String>) = ezCond(expr) { field.like("$it%") }

fun ezAnd(vararg conditions: Condition?): Condition = DSL.and(conditions.filterNotNull())

fun ezOr(vararg conditions: Condition?): Condition = DSL.or(conditions.filterNotNull())