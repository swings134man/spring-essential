package com.makers.princemaker.entity

import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import io.kotest.core.spec.style.StringSpec

/**
 * EntityKoTest.kt:
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 6. 오후 5:47
 * @description: Entity Class 에 대한 테스트
 */
class EntityKoTest: StringSpec ({

    val juniorPrince =
        PrinceMock.createPrince(
            PrinceLevel.JUNIOR_PRINCE, SkillType.INTELLECTUAL,
            PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS, "princeId"
        )

    "Entity Class Test" {
        // given
        val prince = juniorPrince

        // when
        val princeLevel = prince.princeLevel
        val skillType = prince.skillType
        val experienceYears = prince.experienceYears

        // then
        assert(princeLevel == PrinceLevel.JUNIOR_PRINCE)
        assert(skillType == SkillType.INTELLECTUAL)
        assert(experienceYears == PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS)

        println(prince)
    }

    "Entity set Test" {
        val prince = juniorPrince
        prince.age = 20

        println(prince.age)
    }


})