package ez.auth

import org.apache.shiro.subject.Subject

class EzSubject(
    private val subject: Subject
) : Subject by subject {
    override fun getPrincipal(): Any? = subject.principal

    /**
     * principal string. if principal is not string. call toString() to convert it
     */
    val pString: String? = subject.principal as? String ?: subject.principal?.toString()

    fun hasAnyRole(roles: Iterable<String>) = roles.any(::hasRole)

    fun hasAnyRole(vararg roles: String) = hasAnyRole(roles.toList())
}