package com.lucas.kopringjpademo.common

import org.slf4j.LoggerFactory

/**
 * CommonLogger.kt: 공통 로깅 기능
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 7. 12. 오후 3:59
 * @description: Slf4j Logger를 사용하여 공통 로깅 기능을 제공하는 파일
 * - 사용 Class 에서 val logger = logger() 를 사용하여 log 를 사용할 수 있다.
 */
inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!