package dev.mbo.springkotlincache.exception

class NoSuchKeyException(val key: String, message: String) : CacheRedisException(message = message)