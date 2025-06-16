package com.lucas.kopringjpademo.modules.dsl.repository

import com.lucas.kopringjpademo.common.PageResponse
import com.lucas.kopringjpademo.modules.dsl.dto.DslResponseDTO
import com.lucas.kopringjpademo.modules.dsl.dto.toResponseDTO
import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity
import com.lucas.kopringjpademo.modules.dsl.entity.QDslEntity
import com.lucas.kopringjpademo.utils.PagingSupportUtil.applyPaging
import com.lucas.kopringjpademo.utils.withPageable
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

/**
 * DslCustomRepositoryImpl.kt:
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 16. 오후 6:10
 * @description: QuerydslRepositorySupport 의 경우 applyPagination 과 같은 Utility Method 사용 하는 예제 -> 사용하지 않는다면 extends 에서 제외
 */
@Repository
class DslCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : DslCustomRepository, QuerydslRepositorySupport(DslEntity::class.java){

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


    // ---------------------------------------------- Paging ----------------------------------------------

    // 전통적인 Paging 방식 (카운트쿼리포함)
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

    // BEST Practice: PagingUtil 을 사용하여 코드 간소화 (카운트쿼리포함)
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

    // Paging 만을 위한 쿼리 + 확장함수로 Paging 쿼리 공통화 - 페이지 번호, 카운트는 지원하지 않음
    override fun findByNamePagingOnlyData(name: String, pageable: Pageable): List<DslEntity> {
        //
        return queryFactory
            .selectFrom(QDslEntity.dslEntity)
            .where(QDslEntity.dslEntity.name.like("%$name%"))
            .withPageable(pageable)
            .fetch()
    }

    // QuerydslRepositorySupport 를 사용한 Paging - 페이지 번호, 카운트는 지원하지 않음
    override fun findByNameUsingSupport(name: String, pageable: Pageable): List<DslEntity?>? {
        val query =
            queryFactory
                .selectFrom(QDslEntity.dslEntity)
                .where(QDslEntity.dslEntity.name.like("%$name%"))

        return querydsl?.applyPagination(pageable, query)?.fetch()
    }

}