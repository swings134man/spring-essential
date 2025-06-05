package com.makers.princemaker.dto

import com.makers.princemaker.entity.Prince
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


class CreatePrince {

    data class Request(
        @field:NotNull
        val princeLevel: PrinceLevel? = null,

        @field:NotNull
        val skillType: SkillType? = null,

        @field:NotNull
        @field:Min(0)
        val experienceYears: Int? = null,

        @field:NotNull
        @field:Size(min = 3, max = 50, message = "invalid princeId")
        val princeId: String? = null,

        @field:NotNull
        @field:Size(min = 2, max = 50, message = "invalid name")
        val name: String? = null,

        @field:NotNull
        @field:Min(18)
        val age: Int? = null
    )

    class Response(
        val princeLevel: PrinceLevel? = null,
        val skillType: SkillType? = null,
        val experienceYears: Int? = null,
        val princeId: String? = null,
        val name: String? = null,
        val age: Int? = null
    )
}//class

// 확장함수: from entity 형태보다는 이렇게 활용하는게 더 자연스럽다
fun Prince.toCreatePrinceResponse() = CreatePrince.Response(
    princeLevel = this.princeLevel,
    skillType = this.skillType,
    experienceYears = this.experienceYears,
    princeId = this.princeId,
    name = this.name,
    age = this.age
)