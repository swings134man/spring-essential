package com.makers.princemaker.util

import com.makers.princemaker.util.DateTimeUtils.getLocalDateTimeString
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

// KoTest
internal class DateTimeUtilsTest: StringSpec ({

    "getLocalDateTimeString 메서드 테스트" {
        val result = getLocalDateTimeString(
            LocalDateTime.of(2023, 12, 21, 10, 10)
        )

        result shouldBe "2023-12-21 탄생"
    }

})//body