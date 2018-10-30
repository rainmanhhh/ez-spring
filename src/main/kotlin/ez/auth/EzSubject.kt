package ez.auth

import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.subject.Subject

class EzSubject(
    private val subject: Subject
) : Subject by subject {
    @Suppress("UNCHECKED_CAST")
    val principalOrNull: String? = subject.principal as String?

    override fun getPrincipal(): String = principalOrNull ?: throw PrincipalIsNull
}

object PrincipalIsNull : AuthenticationException("principal is null")

