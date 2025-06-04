package com.makers.princemaker.dto

import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType

class EditPrince {

    data class Request(
        var princeLevel: PrinceLevel? = null,
        var skillType: SkillType? = null,
        var experienceYears: Int? = null,
        var name: String? = null,
        var age: Int? = null
    )
}
