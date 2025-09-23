package com.lucas.hexagonalkotlin.application.users.service

import com.lucas.hexagonalkotlin.application.users.commands.UserCommand
import com.lucas.hexagonalkotlin.application.users.mapper.UserCommandMapper
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
    private val usersRepository: UsersRepository,
    private val userMapper: UserCommandMapper
): UsersUseCase{

    // User Save
    @Transactional
    override fun createUser(command: UserCommand.CreateUserCommand): UsersDto =
        usersRepository.createUser(userMapper.toDomain(command))
            .let { UsersDto.fromDomain(it) }

    // user update (Password, phoneNumber 제외)
    @Transactional
    override fun updateUser(command: UserCommand.UpdateUserCommand): UsersDto {

        // domain get
        val domain = usersRepository.findUserById(command.id)
            ?: throw RuntimeException("User not found with id: ${command.id}")

        // domain 에 update 된 Command 값 매핑 - null safe check
        domainUpdate(domain, command)

        return usersRepository.updateUser(domain)
            .let { UsersDto.fromDomain(it) }
    }

    // update Password
    @Transactional
    override fun updateUserPassword(command: UserCommand.UpdateUserPasswordCommand) {
        val find = usersRepository.findUserById(command.id) // domain
            ?: throw RuntimeException("User not found with id: ${command.id}")

        find.changePassword(command.newPassword)

        usersRepository.updateUserPassword(find)
    }

    @Transactional(readOnly = true)
    override fun findAllUsers(): List<UsersDto> =
        usersRepository.findAllUsers().map { UsersDto.fromDomain(it) }


    @Transactional(readOnly = true)
    override fun findUserById(id: Long): UsersDto? =
        usersRepository.findUserById(id)?.let { UsersDto.fromDomain(it) }


    // Domain Update - patch 방식
    private fun domainUpdate(domain: Users, command: UserCommand.UpdateUserCommand) {
        command.email?.let { if (it.isNotBlank()) domain.email = it }
        command.userName?.let { if (it.isNotBlank()) domain.userName = it }
        command.age?.let { if (it >= 0) domain.age = it }
        command.gender?.let { if (it.isNotBlank()) domain.gender = it }
        command.address?.let { if (it.isNotBlank()) domain.address = it }
        command.isActive?.let { domain.isActive = it }
    }

}