package ez.auth

import org.apache.shiro.subject.Subject

/**
 * simple wrapper of shiro [Subject]
 * @property subject Subject
 * @property pString String?
 * @constructor
 */
class EzSubject(
    private val subject: Subject
) : Subject by subject {
    /**
     *
     * @return Any?
     */
    override fun getPrincipal(): Any? = subject.principal

    /**
     * principal string. if principal is not string. call toString() to convert it
     */
    val pString: String? = subject.principal as? String ?: subject.principal?.toString()

    /**
     *
     * @param roles Iterable<String>
     * @return Boolean
     */
    fun hasAnyRole(roles: Iterable<String>): Boolean = roles.any(::hasRole)

    /**
     *
     * @param roles Array<out String>
     * @return Boolean
     */
    fun hasAnyRole(vararg roles: String): Boolean = hasAnyRole(roles.toList())
}