package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode
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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Supplier
import java.util.stream.Collectors

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

        val prince = Prince(
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
        )
        princeRepository.save<Prince?>(prince)
        return prince.toCreatePrinceResponse()
    }

    private fun validateCreatePrinceRequest(request: CreatePrince.Request) {
        princeRepository.findByPrinceId(request.princeId)
            .ifPresent(Consumer { prince: Prince? ->
                throw PrinceMakerException(PrinceMakerErrorCode.DUPLICATED_PRINCE_ID)
            })

        if (request.princeLevel == PrinceLevel.KING
            && request.experienceYears!! < PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
        ) {
            throw PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
        }

        if (request.princeLevel == PrinceLevel.MIDDLE_PRINCE
            && (request.experienceYears!! > PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
                    || request.experienceYears < PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS)
        ) {
            throw PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
        }

        if (request.princeLevel == PrinceLevel.JUNIOR_PRINCE
            && request.experienceYears!! > PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS
        ) {
            throw PrinceMakerException(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
        }
    }

    @get:Transactional
    val allPrince: MutableList<PrinceDto?>
        get() = princeRepository.findByStatusEquals(StatusCode.HEALTHY)
            .stream().map<PrinceDto?> { prince: Prince? -> PrinceDto.fromEntity(prince) }
            .collect(Collectors.toList())

    @Transactional
    fun getPrince(princeId: String?): PrinceDetailDto? {
        return princeRepository.findByPrinceId(princeId)
            .map<PrinceDetailDto?>(Function { prince: Prince? -> prince?.toPrinceDetailDto() })
            .orElseThrow<PrinceMakerException?>(
                Supplier { PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE) }
            )
    }

    @Transactional
    fun editPrince(
        princeId: String?, request: EditPrince.Request
    ): PrinceDetailDto? {
        val prince = princeRepository.findByPrinceId(princeId)
            .orElseThrow<PrinceMakerException?>(
                Supplier { PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE) }
            )
        prince.princeLevel = request.princeLevel!!
        prince.skillType = request.skillType!!
        prince.experienceYears = request.experienceYears!!
        prince.name = request.name!!
        prince.age = request.age!!

        return prince.toPrinceDetailDto()
    }

    @Transactional
    fun woundPrince(
        princeId: String?
    ): PrinceDetailDto? {
        val prince = princeRepository.findByPrinceId(princeId)
            .orElseThrow<PrinceMakerException?>(
                Supplier { PrinceMakerException(PrinceMakerErrorCode.NO_SUCH_PRINCE) }
            )

        prince.status = StatusCode.WOUNDED

        val woundedPrince = WoundedPrince()
            woundedPrince.princeId = prince.princeId
            woundedPrince.name = prince.name
        woundedPrinceRepository.save<WoundedPrince?>(woundedPrince)
        return prince.toPrinceDetailDto()
    }
}
