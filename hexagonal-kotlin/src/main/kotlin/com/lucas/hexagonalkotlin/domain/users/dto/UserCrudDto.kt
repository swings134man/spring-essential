package com.lucas.hexagonalkotlin.domain.users.dto

/**
 * UserCrudDto.kt: User CRUD DTO
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 20. 오후 9:39
 * @description:
 */
data class UserSaveDto (
    val email: String,
    val password: String,
    val userName: String,
    val phoneNumber: String,
    val age: Int,
    var gender: String,
    var address: String,
    var isActive: Boolean = true,
)

// User Update DTO
data class UserUpdateDto(
    val id: Long,
    val email: String?,
    val userName: String?,
    val age: Int?,
    var gender: String?,
    var address: String?,
    var isActive: Boolean?
)

// password update
data class UserPasswordUpdateDto(
    val id: Long,
    val newPassword: String
)

// phone number update
data class UserPhoneNumberUpdateDto(
    val id: Long,
    val newPhoneNumber: String
)

// activate user
data class UserActivateDto(
    val id: Long,
    val phoneNumber: String
)