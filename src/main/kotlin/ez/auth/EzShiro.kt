package ez.auth

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.crypto.SecureRandomNumberGenerator
import org.apache.shiro.crypto.hash.SimpleHash
import org.apache.shiro.util.ByteSource

open class EzShiro(
    private val hashAlgorithm: String = "SHA-256",
    private val hexOrBase64: Boolean = true
) {
    fun getSubject(): EzSubject = EzSubject(SecurityUtils.getSubject())

    private fun ByteSource.encode(): String = if (hexOrBase64) toHex() else toBase64()

    /**
     * add salt to password
     */
    fun addSaltToPassword(password: String, salt: String): String = SimpleHash(hashAlgorithm, password, salt).encode()

    /**
     * generate a new salt
     */
    fun createSalt(): String = SecureRandomNumberGenerator().nextBytes().encode()

    /**
     * 获取当前登录用户的身份信息,未登录则返回null
     */
    fun meOrNull(): String? = getSubject().principalOrNull

    /**
     * 获取当前登录用户的身份信息,未登录则抛异常
     */
    fun me(): String = getSubject().principal

    fun login(token: AuthenticationToken) = getSubject().let {
        it.login(token)
        it.principal
    }

    fun login(username: String, password: String, salt: String, rememberMe: Boolean = false) =
        login(UsernamePasswordToken(username, addSaltToPassword(password, salt), rememberMe))

    fun logout() = getSubject().logout()
}


