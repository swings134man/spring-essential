package com.makers.princemaker.controller

import com.makers.princemaker.dto.CreatePrince
import com.makers.princemaker.dto.EditPrince
import com.makers.princemaker.dto.PrinceDetailDto
import com.makers.princemaker.dto.PrinceDto
import com.makers.princemaker.service.PrinceMakerService
import jakarta.validation.Valid
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
class PrinceMakerController(
    private val princeMakerService: PrinceMakerService
) {

    @PostMapping("/create-prince")
    fun createPrince(
        @Valid @RequestBody request:  CreatePrince.Request
    ): CreatePrince.Response {
        return princeMakerService.createPrince(request)
    }

    @get:GetMapping("/princes")
    val princes: MutableList<PrinceDto?>
        get() = princeMakerService.allPrince

    @GetMapping("/prince/{princeId}")
    fun getPrince(
        @PathVariable princeId: String
    ): PrinceDetailDto? {
        return princeMakerService.getPrince(princeId)
    }

    @PutMapping("/prince/{princeId}")
    fun updatePrince(
        @PathVariable princeId: String,
        @Valid @RequestBody request: EditPrince.Request
    ): PrinceDetailDto? {
        return princeMakerService.editPrince(princeId, request)
    }

    @DeleteMapping("/prince/{princeId}")
    fun deletePrince(
        @PathVariable princeId: String
    ): PrinceDetailDto? {
        return princeMakerService.woundPrince(princeId)
    }
}
