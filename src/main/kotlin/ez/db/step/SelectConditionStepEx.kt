package ez.db.step

import org.jooq.*
import org.jooq.impl.DSL

fun Condition.and(expr: Boolean, provider: () -> Condition) =
    if (expr) and(provider()) else this

fun Condition.or(expr: Boolean, provider: () -> Condition) =
    if (expr) or(provider()) else this

fun Condition.and(action: True.() -> Condition): Condition = DSL.trueCondition().run(action).let {
    if (it == DSL.trueCondition()) this else and(it)
}

fun Condition.or(action: False.() -> Condition): Condition = DSL.falseCondition().run(action).let {
    if (it == DSL.falseCondition()) this else or(it)
}

fun <R : Record> SelectWhereStep<R>.where(builder: True.() -> Condition) =
    where(DSL.trueCondition().run(builder))