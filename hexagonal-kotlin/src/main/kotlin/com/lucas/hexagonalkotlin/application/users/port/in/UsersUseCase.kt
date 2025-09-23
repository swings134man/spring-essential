package com.lucas.hexagonalkotlin.application.users.port.`in`

import com.lucas.hexagonalkotlin.application.users.commands.UserCommand
import com.lucas.hexagonalkotlin.domain.users.dto.UsersDto

/**
 * UsersUseCase.kt:
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:08
 * @description: Adapter 에서 호출하는 UseCase Interface
 */
interface UsersUseCase {
    fun createUser(command: UserCommand.CreateUserCommand): UsersDto
    fun updateUser(command: UserCommand.UpdateUserCommand): UsersDto
    fun updateUserPassword(command: UserCommand.UpdateUserPasswordCommand)
    fun updateUserPhoneNumber(command: UserCommand.UserWithPhoneNumberCommand): UsersDto
    fun findAllUsers(): List<UsersDto>
    fun findUserById(id: Long): UsersDto?
    fun activateUser(command: UserCommand.UserWithPhoneNumberCommand): UsersDto
    fun deactivateUser(id: Long)
}