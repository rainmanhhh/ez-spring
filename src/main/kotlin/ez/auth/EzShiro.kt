package ez.auth

import ez.auth.EzShiro.Encoders
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.authc.credential.CredentialsMatcher
import org.apache.shiro.cache.CacheManager
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.util.ByteSource
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver
import org.apache.shiro.web.servlet.AbstractShiroFilter

typealias Encoder = ((ByteSource) -> String)

abstract class EzShiro @JvmOverloads constructor(
    /**
     * if it's null, use [javaClass].canonicalName
     */
    name: String? = null,
    /**
     * null means don't encode password. default value is [Encoders.HEX]. suggest to encode salt with it too.
     */
    val passwordEncoder: Encoder? = Encoders.HEX,
    /**
     * null means don't encode username. default value is null
     */
    val usernameEncoder: Encoder? = null,
    cacheManager: CacheManager? = null,
    credentialsMatcher: CredentialsMatcher? = null
): AuthorizingRealm(cacheManager, credentialsMatcher) {
    object Encoders {
        val HEX = ByteSource::toHex
        val BASE64 = ByteSource::toBase64
    }

    init {
        super.setName(name ?: this.javaClass.canonicalName)
    }

    /**
     * 返回封装后的shiro授权对象
     * @return EzSubject
     */
    fun me() = EzSubject(SecurityUtils.getSubject())

    private fun Any.toByteSource(): ByteSource = ByteSource.Util.bytes(this)

    fun createToken(username: Any, password: Any, rememberMe: Boolean = false, host: String? = null) =
        UsernamePasswordToken(encodeUsername(username), encodePassword(password), rememberMe, host)

    fun encodeUsername(username: Any) = usernameEncoder?.invoke(username.toByteSource()) ?: username.toString()

    fun encodePassword(password: Any) = passwordEncoder?.invoke(password.toByteSource()) ?: password.toString()

    fun login(username: Any, password: Any, rememberMe: Boolean = false, host: String? = null) =
        me().login(createToken(username, password, rememberMe, host))

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