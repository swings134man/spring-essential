package com.lucas.hexagonalkotlin.adapter.out.persistence.users

import com.lucas.hexagonalkotlin.application.users.port.out.UsersRepository
import com.lucas.hexagonalkotlin.domain.users.model.Users
import org.springframework.stereotype.Component

/**
 * UsersRepositoryAdapter.kt: UsersRepository 구현체
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:16
 * @description: port(out) 에 정의된 UsersRepository 를 implements 하여 구현
 * - 실제 쿼리나, JPA 접근과 관련된 내용은 UsersJpaRepository 에 작성
 */
@Component
class UsersRepositoryAdapter(
    private val usersJpaRepository: UsersJpaRepository
): UsersRepository {

    override fun createUser(domain: Users): Users {
        val entity = usersJpaRepository.save(UsersEntity.fromDomain(domain))
        return entity.toDomain()
    }

    // update User: Password 제외
    override fun updateUser(domain: Users): Users =
        usersJpaRepository.findById(domain.id!!)
            .map { entity ->
                entity.updateWith(domain)
                usersJpaRepository.save(entity).toDomain()
            }
            .orElseThrow { RuntimeException("User not found with id: ${domain.id}") }


    override fun findAllUsers(): List<Users> =
        usersJpaRepository.findAll().map(UsersEntity::toDomain)


    // User 조회 (ID) - 없으면 null
    override fun findUserById(id: Long): Users? =
         usersJpaRepository.findById(id)
                    .map(UsersEntity::toDomain)
                    .orElse(null)?.let { return it }

    // password 수정
    override fun updateUserPassword(domain: Users) {
        usersJpaRepository.updatePasswordById(domain.id!!, domain.getPassword(), domain.updatedAt!!)
    }

}