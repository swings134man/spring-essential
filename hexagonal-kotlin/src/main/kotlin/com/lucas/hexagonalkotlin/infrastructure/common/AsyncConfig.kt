package com.lucas.hexagonalkotlin.infrastructure.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

/**
 * AsyncConfig.kt: Async Config Class
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 26. 오전 2:28
 * @description: 
 */
@EnableAsync
@Configuration
class AsyncConfig {

    @Bean("multiExecutor")
    fun multiExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.queueCapacity = 500 // 대기 queue size
        executor.setThreadNamePrefix("AsyncMulti-")
        executor.initialize()
        return executor
    }
}