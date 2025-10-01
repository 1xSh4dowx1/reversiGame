package storage

import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteRecursively
import kotlin.test.*

class TextFileStorageTests {

    companion object {
        private const val FOLDER = "test-games"
        private val storage = TextFileStorage<String, String>(
            FOLDER,
            object : Serializer<String> {
                override fun serialize(data: String) = data
                override fun deserialize(text: String) = text
            }
        )

        @BeforeTest
        fun setup() {
            Path(FOLDER).createDirectories()
        }

        @AfterTest
        fun cleanup() {
            @OptIn(ExperimentalPathApi::class)
            Path(FOLDER).deleteRecursively()
        }
    }

    @Test
    fun createAndReadEntry() {
        storage.create("g1", "content")
        assertEquals("content", storage.read("g1"))
    }

    @Test
    fun updateEntry() {
        storage.create("g2", "old")
        storage.update("g2", "new")
        assertEquals("new", storage.read("g2"))
    }

    @Test
    fun deleteEntry() {
        storage.create("g3", "x")
        storage.delete("g3")
        assertNull(storage.read("g3"))
    }
}
