package com.lucas.kopringjpademo.utils

/**
 * CommonUtils.kt: 편의 유틸 함수들의 모음
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 7. 11. 오후 6:03
 * @description:
 */

/**
 * @author: lucaskang(swings134man)
 * @since: 2025. 7. 11. 오후 6:03 
 * @description: 현재 스레드의 이름과 함께 메시지를 출력하는 함수
 */
fun <T>printlnWithThreadName(message: T) {
    println("$message - ${Thread.currentThread().name}")
}