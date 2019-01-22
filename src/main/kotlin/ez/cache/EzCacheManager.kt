package ez.cache

import org.springframework.beans.factory.ObjectProvider
import org.springframework.cache.CacheManager

/**
 * @property cacheManager CacheManager
 * @constructor
 */
class EzCacheManager(
    provider: ObjectProvider<CacheManager>
) {
    private val cacheManager: CacheManager =
        provider.ifAvailable ?: throw RuntimeException("cacheManager not available")

    fun getCacheNames(): Collection<String> = cacheManager.cacheNames

    fun getCache(name: String): EzCache {
        val cache = cacheManager.getCache(name)
            ?: throw RuntimeException("cacheName not exist:$name")
        return EzCache(cache)
    }
}