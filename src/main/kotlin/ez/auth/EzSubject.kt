package ez.auth

import org.apache.shiro.subject.Subject

class EzSubject<PRINCIPAL>(
    private val ezShiro: EzShiro<PRINCIPAL>,
    private val subject: Subject
) : Subject by subject {
    @Suppress("UNCHECKED_CAST")
    val principalOrNull: PRINCIPAL? = ezShiro.asPrincipalOrNull(subject.principal)

    override fun getPrincipal(): PRINCIPAL = ezShiro.asPrincipal(subject.principal)
}

