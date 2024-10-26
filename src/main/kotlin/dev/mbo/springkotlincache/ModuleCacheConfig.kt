package dev.mbo.springkotlincache

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [ModuleCacheConfig::class])
open class ModuleCacheConfig