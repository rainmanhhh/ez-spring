package ez.db.step

import ez.db.dsl.EzDsl
import org.jooq.Condition
import org.jooq.Record
import org.jooq.SelectConditionStep

fun <RECORD : Record> SelectConditionStep<RECORD>.ezAnd(
    vararg conditions: Condition?
): SelectConditionStep<RECORD> = and(EzDsl.and(*conditions))

fun <RECORD : Record> SelectConditionStep<RECORD>.ezOr(
    vararg conditions: Condition?
): SelectConditionStep<RECORD> = or(EzDsl.or(*conditions))