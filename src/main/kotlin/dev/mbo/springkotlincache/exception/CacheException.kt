package dev.mbo.springkotlincache.exception

abstract class CacheException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)