package com.lucas.hexagonalkotlin.adapter.out.persistence.users

import com.lucas.hexagonalkotlin.domain.users.model.Users
import com.lucas.hexagonalkotlin.infrastructure.common.Auditable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * UsersEntity.kt: JPA 를 사용하기위한 Users Entity Class
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:10
 * @description: Persistence 레벨(Adapter)에서만 사용함
 */
@Entity
@Table(name = "users")
class UsersEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var email: String,
    var password: String,
    var userName: String,
    var phoneNumber: String,
    var age: Int,
    var gender: String,
    var address: String,
    var isActive: Boolean,
): Auditable() {

    companion object {
        fun fromDomain(user: Users): UsersEntity {
            return UsersEntity(
                id = user.id,
                email = user.email,
                password = user.getPassword(),
                userName = user.userName,
                phoneNumber = user.phoneNumber,
                age = user.age,
                gender = user.gender,
                address = user.address,
                isActive = user.isActive
            ).apply {
                createdAt = user.createdAt
                updatedAt = user.updatedAt
            }
        }
    }

    fun toDomain(): Users {
        return Users(
            id = this.id,
            email = this.email,
            password = this.password,
            userName = this.userName,
            phoneNumber = this.phoneNumber,
            age = this.age,
            gender = this.gender,
            address = this.address,
            isActive = this.isActive,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

}