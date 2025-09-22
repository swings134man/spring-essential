package com.lucas.hexagonalkotlin.application.users.commands

import com.lucas.hexagonalkotlin.domain.users.dto.UserSaveDto
import com.lucas.hexagonalkotlin.domain.users.dto.UserUpdateDto
import com.lucas.hexagonalkotlin.domain.users.dto.UsersDto
import com.lucas.hexagonalkotlin.domain.users.model.Users
import org.mapstruct.Mapper

/**
 * UserCommandMapper.kt: DTO -> Command Mapper
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 22. 오후 4:22
 * @description: DTO -> Command, Command -> Domain 변환 Mapper
 */
@Mapper
interface UserCommandMapper {
    fun toDomain(command: UserCommand.CreateUserCommand): Users
    fun toDomain(command: UserCommand.UpdateUserCommand): Users
    fun toDto(domain: Users): UsersDto

    // DTO -> Command Convert
    fun toCreateCommand(dto: UserSaveDto): UserCommand.CreateUserCommand
    fun toUpdateCommand(dto: UserUpdateDto): UserCommand.UpdateUserCommand

}