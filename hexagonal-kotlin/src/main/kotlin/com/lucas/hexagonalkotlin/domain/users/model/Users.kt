package com.lucas.hexagonalkotlin.domain.users.model

import java.time.LocalDateTime

/**
 * Users.kt: Users 에 대한 Domain Model
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:04
 * @description:
 */
class Users(
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

    init {
        require(email.contains("@")) { "잘못된 이메일 형식" }
        require(!email.isEmpty()){ "이메일은 필수 입력 값입니다." }
        require(age >= 0) { "나이는 음수일 수 없습니다." }
    }

    fun getPassword(): String = password

    fun changePassword(newPassword: String) {
        require(newPassword.length >= 8) { "비밀번호는 최소 8자리 이상이어야 합니다." }
        if (newPassword == password) {
            throw IllegalArgumentException("기존 비밀번호와 동일합니다.")
        }
        password = newPassword
        updatedAt = LocalDateTime.now()
    }

    fun deactivate() {
        if (!isActive) throw IllegalStateException("이미 비활성화된 사용자입니다.")
        isActive = false
        updatedAt = LocalDateTime.now()
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        require(newPhoneNumber.matches(Regex("^010-\\d{4}-\\d{4}\$"))) {
            "전화번호 형식이 올바르지 않습니다."
        }
        phoneNumber = newPhoneNumber
        updatedAt = LocalDateTime.now()
    }
}