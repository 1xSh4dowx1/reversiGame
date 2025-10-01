package storage

/** Generic CRUD interface for storage systems. */
interface Storage<Key, Data: Any> {
    fun create(key: Key, data: Data)
    fun read(key: Key): Data?
    fun update(key: Key, data: Data)
    fun delete(key: Key)
}
