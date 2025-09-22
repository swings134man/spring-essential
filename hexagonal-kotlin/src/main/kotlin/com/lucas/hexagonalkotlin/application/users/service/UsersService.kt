package com.lucas.hexagonalkotlin.application.users.service

import com.lucas.hexagonalkotlin.application.users.commands.UserCommand
import com.lucas.hexagonalkotlin.application.users.commands.UserCommandMapper
import com.lucas.hexagonalkotlin.application.users.port.`in`.UsersUseCase
import com.lucas.hexagonalkotlin.application.users.port.out.UsersRepository
import com.lucas.hexagonalkotlin.domain.users.dto.UsersDto
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
    private val usersRepository: UsersRepository,
    private val userMapper: UserCommandMapper
): UsersUseCase{

    // User Save
    @Transactional
    override fun createUser(command: UserCommand.CreateUserCommand): UsersDto =
        usersRepository.createUser(userMapper.toDomain(command))
            .let { UsersDto.fromDomain(it) }

    // user update (Password 제외)
    @Transactional
    override fun updateUser(command: UserCommand.UpdateUserCommand): UsersDto =
        command.id.let {
            usersRepository.updateUser(userMapper.toDomain(command))
                .let(UsersDto::fromDomain)
        }

    @Transactional(readOnly = true)
    override fun findAllUsers(): List<UsersDto> =
        usersRepository.findAllUsers().map { UsersDto.fromDomain(it) }


    @Transactional(readOnly = true)
    override fun findUserById(id: Long): UsersDto? =
        usersRepository.findUserById(id)?.let { UsersDto.fromDomain(it) }


}