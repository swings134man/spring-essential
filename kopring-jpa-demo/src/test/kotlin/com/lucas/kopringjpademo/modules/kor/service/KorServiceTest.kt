package com.lucas.kopringjpademo.modules.kor.service

import com.lucas.kopringjpademo.modules.kor.entity.KorEntity
import com.lucas.kopringjpademo.modules.kor.repository.KorCommonRepository
import com.lucas.kopringjpademo.modules.kor.repository.KorRepository
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.Optional

class KorServiceTest : BehaviorSpec({

    // MockK
    val korRepository: KorRepository = mockk()
    val korCommonRepository: KorCommonRepository = mockk()

    // service Object
    val korService = KorService(korRepository, korCommonRepository)

    Given("kor - save") {

        val requestEntity = KorEntity(name = "Test", age = 30, isActive = true)
        val updateEntity = KorEntity(id = 1, name = "Test", age = 20, isActive = false)

        // 👉 Mock 동작 정의
        every { korRepository.save(requestEntity) } returns requestEntity
        every { korRepository.save(updateEntity) } returns updateEntity
        every { korRepository.findOneById(1) } returns updateEntity

        // save
        When("korEntity 가 정상 저장 될때(Create)") {
            val result = korService.save(requestEntity)

            Then("저장된 korEntity 를 반환한다") {
                assertSoftly(result){
                    result.name shouldBe "Test"
                    result.age shouldBe 30
                    result.isActive shouldBe true
                }
            }
        }

        // update
        When("korEntity 가 정상 수정 될때(Update)") {
            val result = korService.save(updateEntity)

            Then("수정된 korEntity 를 반환한다") {
                assertSoftly(result){
                    result.id shouldBe 1
                    result.name shouldBe "Test"
                    result.age shouldBe 20
                    result.isActive shouldBe false
                }
            }
        }

        // exception
        When("korEntity 가 없는 경우") {
            every { korRepository.findOneById(2) } returns null

            Then("예외가 발생한다") {
                val exception = runCatching { korService.save(KorEntity(
                    id = 2,
                    name = "asd",
                    age = 20,
                    isActive = false
                )) }
                exception.exceptionOrNull() shouldBe IllegalArgumentException("Kor with id 2 not found")
            }
        }
    }// given - save



    Given("kor - findAll") {
        val korList = listOf(
            KorEntity(id = 1, name = "Test1", age = 30, isActive = true),
            KorEntity(id = 2, name = "Test2", age = 25, isActive = false)
        )

        // 👉 Mock 동작 정의
        every { korRepository.findAll() } returns korList

        When("모든 korEntity 를 조회할때") {
            val result = korService.findAllKor()

            Then("korEntity 리스트를 반환한다") {
                assertSoftly(result){
                    result.size shouldBe 2
                    result[0].name shouldBe "Test1"
                    result[1].name shouldBe "Test2"
                }
            }
        }
    }// given - findAll

    Given("kor - findById") {
        val korEntity = KorEntity(id = 1, name = "Test", age = 30, isActive = true)

        // 👉 Mock 동작 정의
        every { korRepository.findById(1) } returns Optional.of(korEntity)
        every { korRepository.findById(2) } returns Optional.empty()

        When("korEntity 를 id로 조회할때") {
            val result = korService.findById(1)

            Then("korEntity 를 반환한다") {
                assertSoftly(result){
                    it?.id shouldBe 1
                    it?.name shouldBe "Test"
                    it?.age shouldBe 30
                    it?.isActive shouldBe true
                }
            }
        }

        // Exception 은 아래처럼 runCatching || shouldThrow 를 사용해서 예외를 처리할 수 있다.
        When("존재하지 않는 id로 조회할때") {
            every { korRepository.findOneById(2) } returns null
            val exception = shouldThrow<IllegalArgumentException> { korService.findById(2) }

            Then("예외가 발생한다") {
//                val exception = runCatching { korService.findById(2) }
//                exception.exceptionOrNull() shouldBe IllegalArgumentException("Kor with id 2 not found")
                exception shouldBe IllegalArgumentException("Kor with id 2 not found")
            }
        }
    }// given - findById
})
