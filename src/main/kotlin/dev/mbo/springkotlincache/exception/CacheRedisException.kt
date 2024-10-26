package dev.mbo.springkotlincache.exception

open class CacheRedisException(message: String? = null, cause: Throwable? = null) :
    CacheException(message = message, cause = cause)