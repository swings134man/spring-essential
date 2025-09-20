package com.lucas.hexagonalkotlin.application.users.port.out

import com.lucas.hexagonalkotlin.adapter.out.persistence.users.UsersEntity
import com.lucas.hexagonalkotlin.domain.users.model.Users

/**
 * UsersRepository.kt: Users 에 대한 Repository Port(out)
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:09
 * @description: 구현체는 Adapter 의 persistence 패키지에 작성
 */
interface UsersRepository {
    fun createUser(domain: Users): Users
    fun updateUser(domain: Users): Users
}