package com.lucas.hexagonalkotlin.domain.users.dto

import com.lucas.hexagonalkotlin.domain.users.model.Users
import java.time.LocalDateTime

/**
 * UsersDto.kt: Users 정보 공유를 위한 DTO
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 20. 오후 6:30
 * @description: User 의 전체 기본정보를 담는 DTO
 */
data class UsersDto (
    val id: Long? = null,
    val email: String,
    private var password: String,
    var userName: String,
    var phoneNumber: String,
    var age: Int,
    var gender: String,
    var address: String,
    var isActive: Boolean = true,
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
) {

    companion object {
        fun fromDomain(domain: Users): UsersDto {
            return UsersDto(
                id = domain.id,
                email = domain.email,
                password = domain.password,
                userName = domain.userName,
                phoneNumber = domain.phoneNumber,
                age = domain.age,
                gender = domain.gender,
                address = domain.address,
                isActive = domain.isActive,
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt
            )
        }
    }

}
