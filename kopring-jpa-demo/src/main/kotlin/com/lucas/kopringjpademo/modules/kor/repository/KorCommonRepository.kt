package com.lucas.kopringjpademo.modules.kor.repository

import com.lucas.kopringjpademo.common.CommonJpaRepository
import com.lucas.kopringjpademo.modules.kor.entity.KorEntity
import com.lucas.kopringjpademo.modules.kor.entity.QKorEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

/**
 * KorCommonRepository.kt: Common Repository 를 상속받아 JPA + QueryDSL 을 함께 사용하는 Repository
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 19. 오후 4:16
 * @description: SimpleJpaRepository 의 function 들과, QueryDSL 을 함께 사용할 수 있음.
 */
@Repository
class KorCommonRepository(entityManager: EntityManager,
                          queryFactory: JPAQueryFactory
) : CommonJpaRepository<KorEntity, Long>(
    KorEntity::class.java,
    QKorEntity.korEntity,
    queryFactory,
    entityManager
){

    fun findByAgeGreaterThan(age: Int): List<KorEntity> {
        val kor = QKorEntity.korEntity
        return queryFactory
            .selectFrom(kor)
            .where(kor.age.gt(age)) // gt = >, goe = >=
            .fetch()
    }

}
