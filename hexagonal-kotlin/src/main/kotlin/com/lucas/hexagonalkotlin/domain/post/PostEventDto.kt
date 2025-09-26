package com.lucas.hexagonalkotlin.domain.post

import com.lucas.hexagonalkotlin.domain.post.model.Post

/**
 * PostEventDto.kt: Post 도메인에 대한 Event DTO Group
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 26. 오후 5:36
 * @description: 
 */
sealed class PostEventDto {


    data class PostViewIncrementEventDto(
        val id: Long,
        val viewCount: Int
    ): PostEventDto() {
        companion object {
            fun fromDomain(domain: Post): PostViewIncrementEventDto {
                return PostViewIncrementEventDto(
                    id = domain.id!!,
                    viewCount = domain.viewCount
                )
            }
        }
    }


}