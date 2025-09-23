package com.lucas.hexagonalkotlin.application.users.commands


/**
 * UserCommand.kt: DTO -> Domain Model 변환 처리 Command 객체
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:06
 * @description: Write Adapter 에서 -> UseCase 로 전달할때만 사용됨
 * - CommandMapper 를 통해 Domain Model 로 변환된다. {@see UserCommandMapper}
 */
sealed class UserCommand {

    data class CreateUserCommand(
        var email: String,
        var password: String,
        var userName: String,
        var phoneNumber: String,
        var age: Int,
        var gender: String,
        var address: String,
        var isActive: Boolean = true
    ): UserCommand()

    data class UpdateUserCommand(
        var id: Long,
        var email: String?,
        var userName: String?,
        var age: Int?,
        var gender: String?,
        var address: String?,
        var isActive: Boolean?
    ): UserCommand()

    data class UpdateUserPasswordCommand(
        var id: Long,
        var newPassword: String
    ): UserCommand()

    // id, PhoneNumber (activate, updatePhoneNumber)
    data class UserWithPhoneNumberCommand(
        var id: Long,
        var phoneNumber: String
    ): UserCommand()

}