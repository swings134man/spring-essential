package com.lucas.hexagonalkotlin.application.users.service

import com.lucas.hexagonalkotlin.application.users.port.`in`.UsersUseCase
import com.lucas.hexagonalkotlin.application.users.port.out.UsersRepository
import com.lucas.hexagonalkotlin.domain.users.dto.UsersDto
import com.lucas.hexagonalkotlin.domain.users.model.Users
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * UsersService.kt: UsersUseCase 구현체
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:08
 * @description: 실제 비즈니스 로직 구현
 * - Port(Out, Repository) 을 호출하여 DB, 외부 API 등을 호출
 */
@Service
class UsersService(
    private val usersRepository: UsersRepository
): UsersUseCase{

    // User Save
    @Transactional
    override fun createUser(domain: Users): UsersDto =
        usersRepository.createUser(domain)
            .let { UsersDto.fromDomain(it) }

    // user update (Password 제외)
    @Transactional
    override fun updateUser(domain: Users): UsersDto =
        domain.id?.let {
            usersRepository.updateUser(domain)
                .let(UsersDto::fromDomain)
        } ?: throw IllegalArgumentException("User ID must not be null for update.")


}