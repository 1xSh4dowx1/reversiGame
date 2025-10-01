package storage

/** Converts objects to/from textual representation for storage. */
interface Serializer<Data> {
    fun serialize(data: Data): String
    fun deserialize(text: String): Data
}
