package com.lucas.hexagonalkotlin.adapter.out.persistence.users

import org.springframework.data.jpa.repository.JpaRepository

/**
 * UsersJpaRepository.kt: JPA Repository
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 19. 오후 5:12
 * @description: JpaRepository 를 상속하여 구현, 실제 DB CRUD 쿼리와 관련된 내용이 들어감
 */
interface UsersJpaRepository : JpaRepository<UsersEntity, Long>{
}