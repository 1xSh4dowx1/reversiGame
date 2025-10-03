package storage

/** Storage in memory (Doesn't use files). */
class MemoryStorage<K, D : Any> : Storage<K, D> {
    private val map = mutableMapOf<K, D>()

    override fun create(key: K, data: D) {
        check(key !in map) { "$key already exists" }
        map[key] = data
    }

    override fun read(key: K): D? = map[key]

    override fun update(key: K, data: D) {
        check(key in map) { "$key already exists" }
        map[key] = data
    }

    override fun delete(key: K) {
        check(map.remove(key) != null) { "$key already exists" }
    }
}
