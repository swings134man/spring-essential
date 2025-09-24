package com.lucas.hexagonalkotlin.domain.post.model

import java.time.LocalDateTime

/**
 * post.kt: Post 도메인 모델
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 24. 오후 6:06
 * @description: Users 의 Id 를 FK 로 가짐(userId)
 */
class Post (
    var id: Long? = null,
    var title: String,
    var content: String,
    var writer: String,
    var delYn: Char = 'N',
    var viewCount: Int = 0,
    var userId: Long, // fk
    var createdAt: LocalDateTime?,
    var updatedAt: LocalDateTime?
) {
    init {
        require(title.isNotEmpty()) { "제목은 필수 입력 값입니다." }
        require(content.isNotEmpty()) { "내용은 필수 입력 값입니다." }
    }

    fun incrementViewCount() {
        viewCount++
        updatedAt = LocalDateTime.now()
    }

    fun markAsDeleted() {
        if (delYn == 'Y') throw IllegalStateException("이미 삭제된 게시물입니다.")
        delYn = 'Y'
        updatedAt = LocalDateTime.now()
    }

    fun updatePost(newTitle: String, newContent: String, userId: Long) {
        require(this.userId == userId) { "게시물 수정 권한이 없습니다." }
        require(newTitle.isNotEmpty()) { "제목은 필수 입력 값입니다." }
        require(newContent.isNotEmpty()) { "내용은 필수 입력 값입니다." }
        title = newTitle
        content = newContent
        updatedAt = LocalDateTime.now()
    }
}