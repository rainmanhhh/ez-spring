package ez.cache

import org.springframework.cache.Cache
import java.io.Serializable

/**
 * @property cache Cache
 * @property name String
 * @constructor
 */
class EzCache(val cache: Cache) {
    val name: String get() = cache.name
    @Suppress("UNCHECKED_CAST")
    operator fun <V : Serializable> get(key: Any): V? {
        val wrapper = cache[key]
        return if (wrapper == null) null else wrapper.get() as V
    }

    operator fun set(key: Any, value: Serializable?) {
        cache.put(key, value)
    }

    fun put(key: Any, value: Serializable?): Unit = set(key, value)

    @Suppress("UNCHECKED_CAST")
    fun <V : Serializable?> putIfAbsent(key: Any, value: Serializable?): V? {
        val wrapper = cache.putIfAbsent(key, value)
        return if (wrapper == null) null else wrapper.get() as V
    }

    fun evict(key: Any): Unit = cache.evict(key)

    fun clear(): Unit = cache.clear()
}