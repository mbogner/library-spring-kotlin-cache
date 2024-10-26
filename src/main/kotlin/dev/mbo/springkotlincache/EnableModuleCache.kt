package dev.mbo.springkotlincache

import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(ModuleCacheConfig::class)
annotation class EnableModuleCache
