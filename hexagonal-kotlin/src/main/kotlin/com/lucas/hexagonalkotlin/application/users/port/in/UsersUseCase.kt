package com.lucas.hexagonalkotlin.application.users.port.`in`

import com.lucas.hexagonalkotlin.domain.users.dto.UsersDto
import com.lucas.hexagonalkotlin.domain.users.model.Users

/**
 * UsersUseCase.kt:
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:08
 * @description: Adapter 에서 호출하는 UseCase Interface
 */
interface UsersUseCase {
    fun createUser(domain: Users): UsersDto
}