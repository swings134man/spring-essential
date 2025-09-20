package com.lucas.hexagonalkotlin.application.users.commands

import com.lucas.hexagonalkotlin.domain.users.dto.UserSaveDto
import com.lucas.hexagonalkotlin.domain.users.dto.UserUpdateDto
import com.lucas.hexagonalkotlin.domain.users.model.Users

/**
 * UserCommand.kt: DTO -> Domain Model 변환 처리 Command
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:06
 * @description: Write Adapter 에서 -> UseCase 로 전달할때만 사용됨
 */
object UserCommand {

    // ------------------------ Request ------------------------
    // 유저 저장용 DTO -> Domain Model 변환
    fun saveToDomain(request: UserSaveDto): Users {
        return Users(
            id = null,
            email = request.email,
            password = request.password,
            userName = request.userName,
            phoneNumber = request.phoneNumber,
            age = request.age,
            gender = request.gender,
            address = request.address,
            isActive = request.isActive,
            createdAt = null,
            updatedAt = null
        )
    }

    // 유저 수정용 DTO -> Domain Model 변환(TODO: Password 제외)
    fun updateToDomain(request: UserUpdateDto): Users {
        return Users(
            id = request.id,
            email = request.email,
            password = request.password,
            userName = request.userName,
            phoneNumber = request.phoneNumber,
            age = request.age,
            gender = request.gender,
            address = request.address,
            isActive = request.isActive,
            createdAt = null,
            updatedAt = null,
        )
    }

}