package com.makers.princemaker.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * DateTimeUtils.kt: DateTime 관련 object Util Class
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 3. 오후 10:41
 * @description: top-level function 사용시, object 키워드 삭제, @JvmStatic 삭제 - function 만 최상단에 두면 된다
 */
object DateTimeUtils {
    @JvmStatic
    fun getLocalDateTimeString(localDateTime: LocalDateTime): String {
        return localDateTime.format(
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd 탄생")
        )
    }
}

// Extension Function
// 위와 동일한 기능을 하는 확장 함수를 작성할 수 있다.
fun LocalDateTime.toBirthDayString(): String = this.format(
    DateTimeFormatter
        .ofPattern("yyyy-MM-dd 탄생")
)