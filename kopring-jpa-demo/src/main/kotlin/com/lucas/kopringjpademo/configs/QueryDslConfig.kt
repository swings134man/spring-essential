package com.lucas.kopringjpademo.configs

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * QueryDslConfig.kt: QueryDsl 를 사용하기 위한 EM 설정 클래스
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 12. 오전 2:47
 * @description:
 */
@Configuration
class QueryDslConfig(
    @PersistenceContext
    val entityManager: EntityManager
) {

    @Bean
    fun queryFactory() = JPAQueryFactory(entityManager)
}