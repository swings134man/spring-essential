package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode
import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.dto.CreatePrince
import com.makers.princemaker.entity.PrinceMock
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

// KoTest: Service Test Class
class PrinceMakerServiceKoTest : BehaviorSpec({

    // MockK 로 Mock 객체 생성
    val princeRepository: PrinceRepository = mockk()
    val woundedPrinceRepository: WoundedPrinceRepository = mockk()

    // Service 객체 생성: Mock 객체 직접 주입
    val princeMakerService = PrinceMakerService(
        princeRepository = princeRepository,
        woundedPrinceRepository = woundedPrinceRepository
    )

    Given("프린스 생성 진행시") {

        val request = CreatePrince.Request(
            PrinceLevel.JUNIOR_PRINCE,
            SkillType.INTELLECTUAL,
            3,
            "princeId",
            "name",
            28
        )

        val juniorPrince = PrinceMock.createPrince(
                PrinceLevel.JUNIOR_PRINCE,
                SkillType.INTELLECTUAL,
                PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS,
                "princeId"
            )

        // Repository Mock 설정
        every { princeRepository.save(any()) } returns juniorPrince

        When("princeId 가 중복되지 않고 정상요청"){
            every {
                princeRepository.findByPrinceId(any())
            } returns null

            val result = princeMakerService.createPrince(request)
            Then("정상 응답"){
                assertSoftly(result) {
                    princeLevel shouldBe PrinceLevel.JUNIOR_PRINCE
                    skillType shouldBe SkillType.INTELLECTUAL
                    experienceYears shouldBe 3
                }
            }
        }



        When("princeId 가 중복되고 정상요청"){
            every {
                princeRepository.findByPrinceId(any())
            } returns juniorPrince

            val ex = shouldThrow< PrinceMakerException> {
                princeMakerService.createPrince(request)
            }

            Then("중복 오류 응답"){
                ex.princeMakerErrorCode shouldBe PrinceMakerErrorCode.DUPLICATED_PRINCE_ID
            }
        }
    }

})
