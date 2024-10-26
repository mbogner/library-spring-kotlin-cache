package dev.mbo.springkotlincache

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.ObjectMapper
import dev.mbo.springkotlincache.exception.CacheJsonException
import dev.mbo.springkotlincache.exception.CacheRedisException
import dev.mbo.springkotlincache.exception.NoSuchKeyException
import io.lettuce.core.RedisException
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
private class CacheRedis<T>(
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper,
) : Cache<T> {

    override fun add(key: String, value: T, timeout: Long?, timeUnit: TimeUnit) {
        exceptionWrapper {
            val json = objectMapper.writeValueAsString(value)
            @SuppressWarnings("kotlin:S6518")
            if (null == timeout) {
                redisTemplate.opsForValue()[key] = json
            } else {
                redisTemplate.opsForValue().set(key, json, timeout, timeUnit)
            }
        }
    }

    override fun get(key: String, targetClass: Class<T>): T {
        return exceptionWrapper {
            val entry = redisTemplate.opsForValue()[key] ?: throw NoSuchKeyException(key, "no entry found for $key")
            objectMapper.readValue(entry, targetClass)
        }
    }

    override fun delete(key: String) {
        exceptionWrapper {
            redisTemplate.delete(key)
        }
    }

    private fun <R> exceptionWrapper(method: () -> R): R {
        return try {
            method.invoke()
        } catch (exc: JacksonException) {
            throw CacheJsonException(exc)
        } catch (exc: RedisException) {
            throw CacheRedisException(cause = exc)
        }
    }

}