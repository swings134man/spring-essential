package com.makers.princemaker.dto

import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import com.makers.princemaker.util.toBirthDayString

data class PrinceDetailDto(
             var princeLevel: PrinceLevel? = null,
             var skillType: SkillType? = null,
             var experienceYears: Int? = null,
             var princeId: String? = null,
             var name: String? = null,
             var age: Int? = null,
             var status: StatusCode? = null,
             var birthDate: String? = null,
)

fun Prince.toPrinceDetailDto() = PrinceDetailDto(
    princeLevel = this.princeLevel,
    skillType = this.skillType,
    experienceYears = this.experienceYears,
    princeId = this.princeId,
    name = this.name,
    age = this.age,
    status = this.status,
    birthDate = this.createdAt!!.toBirthDayString()
)