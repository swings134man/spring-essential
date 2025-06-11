package com.lucas.kopringjpademo.modules.dsl.repository

import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity
import com.lucas.kopringjpademo.modules.dsl.entity.QDslEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class DslCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : DslCustomRepository{

    override fun findByNameLike(name: String): List<DslEntity> {
        return queryFactory
            .selectFrom(QDslEntity.dslEntity)
            .where(QDslEntity.dslEntity.name.like("%$name%"))
            .fetch()
    }

    override fun findByNameIn(names: List<String>): List<DslEntity> {
        return queryFactory
            .selectFrom(QDslEntity.dslEntity)
            .where(QDslEntity.dslEntity.name.`in`(names))
            .fetch()
    }
}