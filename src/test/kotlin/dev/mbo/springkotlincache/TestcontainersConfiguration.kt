package dev.mbo.springkotlincache

import com.redis.testcontainers.RedisContainer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
open class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    open fun minioContainer(): RedisContainer {
        val redis = RedisContainer(DockerImageName.parse("redis:7-alpine"))
        redis.start()
        return redis
    }

}
