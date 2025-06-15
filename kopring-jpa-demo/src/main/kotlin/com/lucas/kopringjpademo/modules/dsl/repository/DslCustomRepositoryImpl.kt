package com.lucas.kopringjpademo.modules.dsl.repository

import com.lucas.kopringjpademo.common.PageResponse
import com.lucas.kopringjpademo.modules.dsl.dto.DslResponseDTO
import com.lucas.kopringjpademo.modules.dsl.dto.toResponseDTO
import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity
import com.lucas.kopringjpademo.modules.dsl.entity.QDslEntity
import com.lucas.kopringjpademo.utils.PagingSupportUtil.applyPaging
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
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

    // DTO Response
    override fun findByAddressLike(address: String): List<DslResponseDTO> {
        return queryFactory
            .selectFrom(QDslEntity.dslEntity)
            .where(QDslEntity.dslEntity.address.like("%$address%"))
            .fetch()
            .map { it.toResponseDTO() }
    }

    // Paging 의 경우 전통적인 방법론으로는: content 쿼리와 count 쿼리를 따로 실행 후 Page 타입으로 return 해야함.
    override fun findByNamePaging(name: String, pageable: Pageable): Page<DslEntity> {
        val content = queryFactory
            .selectFrom(QDslEntity.dslEntity)
            .where(QDslEntity.dslEntity.name.like("%$name%"))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = queryFactory
            .select(QDslEntity.dslEntity.count())
            .from(QDslEntity.dslEntity)
            .where(QDslEntity.dslEntity.name.like("%$name%"))
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, count)
    }

    // PagingUtil 을 사용하여 코드 간소화
    override fun findByNamePagingUtil(
        name: String,
        pageable: Pageable
    ): PageResponse<DslEntity> {
        return applyPaging(
            queryFactory = queryFactory,
            pageable = pageable,
            contentQuery = {
                queryFactory
                    .selectFrom(QDslEntity.dslEntity)
                    .where(QDslEntity.dslEntity.name.like("%$name%"))
            },
            countQuery = {
                queryFactory
                    .select(QDslEntity.dslEntity.count())
                    .from(QDslEntity.dslEntity)
                    .where(QDslEntity.dslEntity.name.like("%$name%"))
            }
        )
    }

}