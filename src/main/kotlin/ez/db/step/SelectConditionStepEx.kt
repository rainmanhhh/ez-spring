package ez.db.step

import ez.db.dsl.EzDsl
import org.jooq.Condition
import org.jooq.Record
import org.jooq.SelectConditionStep

fun <RECORD : Record> SelectConditionStep<RECORD>.ezAnd(
    vararg conditions: Condition?
): SelectConditionStep<RECORD> = EzDsl.and(*conditions).let {
    if (it == null) this else and(it)
}

fun <RECORD : Record> SelectConditionStep<RECORD>.ezOr(
    vararg conditions: Condition?
): SelectConditionStep<RECORD> = EzDsl.or(*conditions).let {
    if (it == null) this else or(it)
}