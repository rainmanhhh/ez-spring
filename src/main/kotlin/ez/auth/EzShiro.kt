package ez.auth

import ez.auth.EzShiro.Encoders
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.crypto.hash.SimpleHash
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.util.ByteSource
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver
import org.apache.shiro.web.servlet.AbstractShiroFilter

typealias Encoder = ((ByteSource) -> String)

open class EzShiro @JvmOverloads constructor(
    /**
     * default algorithm is "SHA-256"
     */
    private val hashAlgorithm: String = "SHA-256",
    /**
     * less than 1 means don't hash password. default value is 1
     */
    private val hashTimes: Int = 1,
    /**
     * null means don't encode password. default value is [Encoders.HEX]
     */
    private val passwordEncoder: Encoder? = Encoders.HEX,
    /**
     * null means don't encode username. default value is null
     */
    private val usernameEncoder: Encoder? = null
) {
    object Encoders {
        val HEX = ByteSource::toHex
        val BASE64 = ByteSource::toBase64
    }

    /**
     * 返回封装后的shiro授权对象
     * @return EzSubject
     */
    fun me() = EzSubject(SecurityUtils.getSubject())

    private fun Any.toByteSource(): ByteSource = ByteSource.Util.bytes(this)
    private fun ByteSource.tryHash(salt: Any? = null, times: Int = 1): ByteSource =
        if (times < 1) this else SimpleHash(hashAlgorithm, this, salt, times)

    fun token(username: Any, password: Any, salt: Any?, rememberMe: Boolean = false, host: String? = null) =
        UsernamePasswordToken(
            usernameEncoder?.invoke(username.toByteSource()) ?: username.toString(),
            passwordEncoder?.invoke(password.toByteSource().tryHash(salt, hashTimes)) ?: password.toString(),
            rememberMe,
            host
        )

    fun login(username: Any, password: Any, salt: Any?, rememberMe: Boolean = false, host: String? = null) =
        me().login(token(username, password, salt, rememberMe, host))

    private fun ShiroFilterFactoryBean.chainManager(): DefaultFilterChainManager {
        val shiroFilter = `object` as AbstractShiroFilter
        val filterChainResolver = shiroFilter.filterChainResolver as PathMatchingFilterChainResolver
        return filterChainResolver.filterChainManager as DefaultFilterChainManager
    }

    /**
     * after [ShiroFilterFactoryBean.filterChainDefinitionMap] changed, call this method to recreate shiro filter chain
     * @param factoryBean ShiroFilterFactoryBean
     */
    @Synchronized
    fun flushFilterChain(factoryBean: ShiroFilterFactoryBean) = factoryBean.run {
        val chainManager = chainManager()
        chainManager.filterChains.clear()
        filterChainDefinitionMap.forEach { k, v ->
            chainManager.createChain(k, v.trim().replace(" ", ""))
        }
    }
}