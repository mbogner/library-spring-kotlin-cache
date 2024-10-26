package dev.mbo.springkotlincache

import dev.mbo.springkotlincache.exception.NoSuchKeyException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.time.Instant
import java.util.UUID

@Import(TestcontainersConfiguration::class)
@SpringBootTest(classes = [TestApplication::class])
class CacheTest @Autowired constructor(
    private val cache: Cache<TestData>
) {

    data class TestData(
        val name: String = UUID.randomUUID().toString(),
        val createdAt: Instant = Instant.now(),
    )

    @Test
    fun addGetRemove() {
        val key = UUID.randomUUID().toString()

        // create
        val data1 = TestData()
        cache.add(key = key, data1)

        // read
        val retrieved: TestData = cache.get(key = key, targetClass = TestData::class.java)
        assertThat(retrieved).isEqualTo(data1)

        // update
        val data2 = TestData()
        assertThat(data2).isNotEqualTo(data1)
        cache.add(key = key, data2)
        assertThat(cache.get(key = key, targetClass = TestData::class.java)).isEqualTo(data2)

        // delete
        cache.delete(key = key)
        assertThrows(NoSuchKeyException::class.java) {
            cache.get(key = key, targetClass = TestData::class.java)
        }
    }

}