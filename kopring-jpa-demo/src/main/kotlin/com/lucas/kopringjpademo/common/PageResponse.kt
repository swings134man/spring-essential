package com.lucas.kopringjpademo.common

import org.springframework.data.domain.Page

/**
 * PageResponse.kt: QueryDSL 을 사용할떄 Pageable 을 사용하기 위한 DTO 클래스
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 13. 오후 5:49
 * @description: Pageable 의 Return Type 을 공통으로 사용하여 (경고, 에러방지, Paging 의 공통화(일관성)) 을 보장함
 */
data class PageResponse<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val page: Int,
    val size: Int
)

// org.springframework.data.domain.Page 타입을 PageResponse 타입으로 변환하는 Extension Function
fun <T> Page<T>.toPageResponse(): PageResponse<T> = PageResponse(
    content = content,
    totalElements = totalElements,
    totalPages = totalPages,
    page = number,
    size = size
)