package ez.db.step

import org.jooq.Condition
import org.jooq.Field
import org.jooq.Record
import org.jooq.SelectConditionStep

/**
 * if value is not null nor "" nor false, add condition: conditionProvider(value)
 * @receiver SelectConditionStep<R>
 * @param value V?
 * @param conditionProvider (V) -> Condition
 * @return SelectConditionStep<R>
 */
fun <R : Record, V : Any> SelectConditionStep<R>.ezAnd(
    value: V?, conditionProvider: (V) -> Condition
): SelectConditionStep<R> = kotlin.run {
    if (value == null || value == false || value == "") this
    else and(conditionProvider(value))
}

/**
 * if value is not null nor "" nor false, add condition: field = value
 * @receiver SelectConditionStep<R>
 * @param field Field<V>
 * @param value V?
 * @return SelectConditionStep<R>
 */
fun <R : Record, V : Any> SelectConditionStep<R>.ezEq(
    field: Field<V>, value: V?
) = ezAnd(value) { field.eq(value) }

/**
 * if value is not null nor "", add condition: field like '%value%'
 * @receiver SelectConditionStep<R>
 * @param field Field<String>
 * @param value String?
 * @return SelectConditionStep<R>
 */
fun <R : Record> SelectConditionStep<R>.ezContains(
    field: Field<String>, value: String?
) = ezAnd(value) { field.like("%$value%") }

/**
 * if value is not null nor "", add condition: field like 'value%'
 * @receiver SelectConditionStep<R>
 * @param field Field<String>
 * @param value String?
 * @return SelectConditionStep<R>
 */
fun <R : Record> SelectConditionStep<R>.ezStartWith(
    field: Field<String>, value: String?
) = ezAnd(value) { field.like("$value%") }

fun <R : Record, V : Any> SelectConditionStep<R>.ezIn(
    field: Field<V>, values: Collection<V>
) = ezAnd(values) { field.`in`(values) }

fun <R : Record, V : Any> SelectConditionStep<R>.ezIn(
    field: Field<V>, vararg values: V
) = ezAnd(values) { field.`in`(*values) }