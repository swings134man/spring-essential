package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.dto.CreatePrince
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.PrinceMock
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.*

@ExtendWith(MockKExtension::class)
internal class PrinceMakerServiceTest {

    // lateinit var: Not null 타입으로 초기화가 지연되는 변수
    @RelaxedMockK
    lateinit var princeRepository: PrinceRepository

    @MockK
    lateinit var woundedPrinceRepository: WoundedPrinceRepository // 사용하지 않아도, 주입하지 않으면 컴파일 에러 발생

    @InjectMockKs
    lateinit var princeMakerService: PrinceMakerService

    @Test
    fun getPrinceTest() {
        //given
        val juniorPrince =
            PrinceMock.createPrince(
                PrinceLevel.JUNIOR_PRINCE, SkillType.INTELLECTUAL,
                PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS, "princeId"
            )

        every { princeRepository.findByPrinceId(any()) }
            .returns(juniorPrince)

        //when
        val prince = princeMakerService.getPrince("princeId")

        //then
        assertEquals(PrinceLevel.JUNIOR_PRINCE, prince.princeLevel)
        assertEquals(SkillType.INTELLECTUAL, prince.skillType)
        assertEquals(PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS, prince.experienceYears)
    }

    @Test
    fun createPrinceTest_success() {
        //given
        val request = CreatePrince.Request(
            PrinceLevel.MIDDLE_PRINCE,
            SkillType.INTELLECTUAL,
            7,
            "princeId",
            "name",
            28
        )

        every { princeRepository.findByPrinceId(any()) } returns null

        val slot = slot<Prince>()
        every { princeRepository.save(any()) } returns Prince(
            id = null,
            princeLevel = PrinceLevel.BABY_PRINCE,
            skillType = SkillType.WARRIOR,
            status = StatusCode.HEALTHY,
            experienceYears = 2,
            princeId = "vestibulum",
            name = "Carmela Shelton",
            age = 11,
            createdAt = null,
            updatedAt = null
        )

        //when
        val response = princeMakerService.createPrince(request)

        //then
        verify(exactly = 1) { princeRepository.save(capture(slot))}

        val savedPrince = slot.captured
        assertEquals(PrinceLevel.MIDDLE_PRINCE, savedPrince.princeLevel)
        assertEquals(SkillType.INTELLECTUAL, savedPrince.skillType)
        assertEquals(7, savedPrince.experienceYears)

        assertEquals(PrinceLevel.MIDDLE_PRINCE, response.princeLevel)
        assertEquals(SkillType.INTELLECTUAL, response.skillType)
        assertEquals(7, response.experienceYears)
    }

    @Test
    fun createPrinceTest_failed_with_duplicated() {
        //given
        val juniorPrince =
            PrinceMock.createPrince(
                PrinceLevel.JUNIOR_PRINCE,
                SkillType.INTELLECTUAL,
                PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS,
                "princeId"
            )
        val request = CreatePrince.Request(
            PrinceLevel.JUNIOR_PRINCE,
            SkillType.INTELLECTUAL,
            3,
            "princeId",
            "name",
            28
        )

        every { princeRepository.findByPrinceId(any()) } returns juniorPrince

        //when
        val exception = assertThrows<PrinceMakerException>(
                PrinceMakerException::class.java,
                Executable { princeMakerService.createPrince(request) })
        //then
        assertEquals(PrinceMakerErrorCode.DUPLICATED_PRINCE_ID, exception.princeMakerErrorCode)
    }

    @Test
    fun createPrinceTest_failed_with_invalid_experience() {
        //given
        val request = CreatePrince.Request(
            PrinceLevel.KING,
            SkillType.INTELLECTUAL,
            PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS - 3,
            "princeId",
            "name",
            28
        )

        every { princeRepository.findByPrinceId(any()) } returns null

        //when
        val exception =
            assertThrows<PrinceMakerException>(
                PrinceMakerException::class.java,
                Executable { princeMakerService.createPrince(request) })
        //then
        assertEquals(
            PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH,
            exception.princeMakerErrorCode
        )
    }
}
