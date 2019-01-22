package ez.db.step

import org.jooq.*
import org.jooq.impl.DSL

/**
 *
 * @receiver Condition
 * @param expr Boolean
 * @param provider () -> Condition
 * @return Condition
 */
fun Condition.and(expr: Boolean, provider: () -> Condition): Condition =
    if (expr) and(provider()) else this

/**
 *
 * @receiver Condition
 * @param expr Boolean
 * @param provider () -> Condition
 * @return Condition
 */
fun Condition.or(expr: Boolean, provider: () -> Condition): Condition =
    if (expr) or(provider()) else this

/**
 *
 * @receiver Condition
 * @param action True.() -> Condition
 * @return Condition
 */
fun Condition.and(action: True.() -> Condition): Condition = DSL.trueCondition().run(action).let {
    if (it == DSL.trueCondition()) this else and(it)
}

/**
 *
 * @receiver Condition
 * @param action False.() -> Condition
 * @return Condition
 */
fun Condition.or(action: False.() -> Condition): Condition = DSL.falseCondition().run(action).let {
    if (it == DSL.falseCondition()) this else or(it)
}

/**
 *
 * @receiver SelectWhereStep<R>
 * @param builder True.() -> Condition
 * @return (org.jooq.SelectConditionStep<(R..R?)>..org.jooq.SelectConditionStep<(R..R?)>?)
 */
@Deprecated("renamed to ezWhere", replaceWith = ReplaceWith("ezWhere", "ez.db.step.ezWhere"))
fun <R : Record> SelectWhereStep<R>.where(builder: True.() -> Condition): SelectConditionStep<R> =
    where(DSL.trueCondition().run(builder))

/**
 *
 * @receiver SelectWhereStep<R>
 * @param builder True.() -> Condition
 * @return SelectConditionStep<R>
 */
fun <R : Record> SelectWhereStep<R>.ezWhere(builder: True.() -> Condition): SelectConditionStep<R> =
    where(DSL.trueCondition().run(builder))
