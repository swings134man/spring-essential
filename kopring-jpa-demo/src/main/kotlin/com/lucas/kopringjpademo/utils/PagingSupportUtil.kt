package com.lucas.kopringjpademo.utils

import com.lucas.kopringjpademo.common.PageResponse
import com.lucas.kopringjpademo.common.toPageResponse
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * PagingSupportUtil.kt: QueryDSL 에서 Paging 관련 쿼리, 리턴 타입을 공통으로 지원하기 위한 유틸 클래스
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 13. 오후 7:37
 * @description: TODO: Join 쿼리 테스트 필요함
 */
object PagingSupportUtil {

    fun <T: Any> applyPaging (
        queryFactory: JPAQueryFactory,
        pageable: Pageable,
        contentQuery: () -> JPAQuery<T>,
        countQuery: (() -> JPAQuery<Long>)? = null
    ) : PageResponse<T>{
        val content = contentQuery()
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val count = countQuery?.invoke()?.fetchOne() ?: 0L

        return PageImpl(content, pageable, count).toPageResponse()
    }

}

// QueryDSL 쿼리 작성시 Chaining 방식으로 Pageable 을 적용할 수 있는 확장 함수
fun <T> JPAQuery<T>.withPageable(pageable: Pageable): JPAQuery<T> {
    return this.limit(pageable.pageSize.toLong())
        .offset(pageable.offset)
}

