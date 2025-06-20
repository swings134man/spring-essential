package com.lucas.kopringjpademo.common

import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.stereotype.Repository

/**
 * CommonJpaRepository.kt: Jpa Repository + QueryDSL Base Repository
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 19. 오전 1:20
 * @description: 해당 클래스를 상속받아 Entity 별로 Common Repository를 구현할 수 있습니다.
 * Jpa Repository 의 기본기능과, QueryDSL 을 함께 사용할 수 있게하는 Base Repository 의 기능을 수행함.
 * - 다만 JPA 가 기본으로 지원하는 findBy... 이런 Method 이름으로 자동 작성되는 쿼리는 불가.
 *
 * - Predicate 구현체를 만들어 Map<String, Any> 형태의 파라미터를 {} 안에 구현할 수 있음..(Not Yet)
 */
@Repository
abstract class CommonJpaRepository<T, ID>(
    clazz: Class<T>,
    val entity: EntityPathBase<T>,
    val queryFactory: JPAQueryFactory,
    entityManager: EntityManager
) : SimpleJpaRepository<T, ID>(clazz, entityManager)

