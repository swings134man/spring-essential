package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode.*
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.dto.CreatePrince
import com.makers.princemaker.dto.EditPrince
import com.makers.princemaker.dto.PrinceDetailDto
import com.makers.princemaker.dto.PrinceDto
import com.makers.princemaker.dto.toCreatePrinceResponse
import com.makers.princemaker.dto.toPrinceDetailDto
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.WoundedPrince
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.util.shouldNotTrue
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * PrinceMakerService.kt: Service Class
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 4. 오후 3:55
 * @description: 의존성 주입은 주 생성자 주입으로 하도록
 */
@Service
class PrinceMakerService(
    private val princeRepository: PrinceRepository,
    private val woundedPrinceRepository: WoundedPrinceRepository
) {

    @Transactional
    fun createPrince(request: CreatePrince.Request): CreatePrince.Response {
        validateCreatePrinceRequest(request)

        return Prince(
            null,
            request.princeLevel!!,
            request.skillType!!,
            StatusCode.HEALTHY,
            request.experienceYears!!,
            request.princeId!!,
            request.name!!,
            request.age!!,
            null,
            null
        ).also {
            princeRepository.save(it)
        }.toCreatePrinceResponse()
    }

    private fun validateCreatePrinceRequest(request: CreatePrince.Request) {
        // ID 가 겹친다면 Exception 발생 => null 이 아니면 true
        (princeRepository.findByPrinceId(request.princeId!!) != null)
            .shouldNotTrue(DUPLICATED_PRINCE_ID)

        (request.princeLevel == PrinceLevel.KING
            && request.experienceYears!! < PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
        ).shouldNotTrue(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)

        (request.princeLevel == PrinceLevel.MIDDLE_PRINCE
            && (request.experienceYears!! > PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
                    || request.experienceYears < PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS)
        ).shouldNotTrue(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)

        (request.princeLevel == PrinceLevel.JUNIOR_PRINCE
            && request.experienceYears!! > PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS
        ).shouldNotTrue(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
    }

    @get:Transactional
    val allPrince: List<PrinceDto>
        get() = princeRepository.findByStatusEquals(StatusCode.HEALTHY)
            .map { PrinceDto.fromEntity(it) }

    @Transactional
    fun getPrince(princeId: String): PrinceDetailDto {
        return princeRepository.findByPrinceId(princeId)
            ?.let { prince: Prince -> prince.toPrinceDetailDto() }              // data 가 존재할떄
            ?: throw PrinceMakerException(NO_SUCH_PRINCE)  // data 가 존재하지 않을때
    }

    @Transactional
    fun editPrince(
        princeId: String, request: EditPrince.Request
    ): PrinceDetailDto {
        val prince = princeRepository.findByPrinceId(princeId)
            ?: throw PrinceMakerException(NO_SUCH_PRINCE)

        // 단헌번 초기화 및 영역 분리
        prince.apply {
            prince.princeLevel = request.princeLevel!!
            prince.skillType = request.skillType!!
            prince.experienceYears = request.experienceYears!!
            prince.name = request.name!!
            prince.age = request.age!!
        }

        return prince.toPrinceDetailDto()
    }

    @Transactional
    fun woundPrince(princeId: String): PrinceDetailDto {

        // 여러번 쓰는 값 영역 분리
        // with 는 마지막에 있는 값을 반환하기에 return 문이 필요없음
        return with(princeRepository.findByPrinceId(princeId)
            ?: throw PrinceMakerException(NO_SUCH_PRINCE)
        ) {
            this.status = StatusCode.WOUNDED

            WoundedPrince(
                null,
                this.princeId,
                this.name,
                null,
                null
            ).also {
                woundedPrinceRepository.save(it)
            }
            this.toPrinceDetailDto()
        }
    }
}
