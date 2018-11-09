package ez.db.condition

import ez.db.dsl.EzDsl
import org.jooq.Field

open class EzField<E : Any>(protected val field: Field<E>) {
    fun eq(expr: E?) = EzDsl.condition(expr, field::eq)
}

class EzStringField(field: Field<String>) : EzField<String>(field) {
    fun contains(expr: String?) = EzDsl.condition(expr) { field.like("%$it%") }
    fun startWith(expr: String?) = EzDsl.condition(expr) { field.like("$it%") }
}

fun <E : Any> Field<E>.ez() = EzField(this)
fun Field<String>.ez() = EzStringField(this)

