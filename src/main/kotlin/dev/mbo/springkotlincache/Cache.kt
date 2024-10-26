package dev.mbo.springkotlincache

import java.util.concurrent.TimeUnit

interface Cache<T> {
    /**
     * Add or update a key with given value. Allows to set a retention time for the entry.
     * @throws dev.mbo.springkotlincache.exception.CacheException
     */
    fun add(key: String, value: T, timeout: Long? = null, timeUnit: TimeUnit = TimeUnit.SECONDS)

    /**
     * Retrieve a value by its key.
     * @throws dev.mbo.springkotlincache.exception.CacheException
     */
    fun get(key: String, targetClass: Class<T>): T

    /**
     * Delete a value by its key.
     * @throws dev.mbo.springkotlincache.exception.CacheException
     */
    fun delete(key: String)
}