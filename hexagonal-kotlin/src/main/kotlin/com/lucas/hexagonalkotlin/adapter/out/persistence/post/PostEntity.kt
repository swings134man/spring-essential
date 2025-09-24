package com.lucas.hexagonalkotlin.adapter.out.persistence.post

import com.lucas.hexagonalkotlin.domain.post.model.Post
import com.lucas.hexagonalkotlin.infrastructure.common.Auditable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "post")
class PostEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var content: String,
    var writer: String?,
    @Column(name = "del_yn" , columnDefinition = "CHAR(1) DEFAULT 'N'")
    var delYn: String = "N",
    @Column(name = "view_count", columnDefinition = "INT DEFAULT 0")
    var viewCount: Int = 0,
    var userId: Long, // fk
): Auditable() {

    companion object {
        fun fromDomain(post: Post): PostEntity {
            return PostEntity(
                id = post.id,
                title = post.title,
                content = post.content,
                writer = post.writer,
                delYn = post.delYn,
                viewCount = post.viewCount,
                userId = post.userId
            ).apply {
                createdAt = post.createdAt
                updatedAt = post.updatedAt
            }
        }
    }

    fun toDomain(): Post {
        return Post(
            id = this.id,
            title = this.title,
            content = this.content,
            writer = this.writer,
            delYn = this.delYn,
            viewCount = this.viewCount,
            userId = this.userId,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    fun updateWith(domain: Post) {
        this.title = domain.title
        this.content = domain.content
        this.writer = domain.writer
        this.delYn = domain.delYn
        this.viewCount = domain.viewCount
        this.userId = domain.userId
        this.createdAt = domain.createdAt
        this.updatedAt = domain.updatedAt
    }
}