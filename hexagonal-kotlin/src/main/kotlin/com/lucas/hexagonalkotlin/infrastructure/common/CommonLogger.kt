package com.lucas.hexagonalkotlin.infrastructure.common

import org.slf4j.LoggerFactory

/**
 * CommonLogger.kt: 공통 로깅
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 4:28
 * @description: val logger = logger()
 */
inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!