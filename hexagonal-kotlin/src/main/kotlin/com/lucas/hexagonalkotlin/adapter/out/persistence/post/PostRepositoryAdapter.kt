package com.lucas.hexagonalkotlin.adapter.out.persistence.post

import com.lucas.hexagonalkotlin.application.post.port.out.PostRepository
import com.lucas.hexagonalkotlin.domain.post.model.Post
import com.lucas.hexagonalkotlin.infrastructure.common.logger
import org.springframework.stereotype.Component

/**
 * PostRepositoryAdapter.kt: Repository Adapter
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 25. 오전 12:46
 * @description: 
 */
@Component
class PostRepositoryAdapter(
    private val postJpaRepository: PostJpaRepository
): PostRepository {

    val logger = logger()

    override fun createPost(domain: Post): Post =
        postJpaRepository.save(PostEntity.fromDomain(domain))
            .toDomain()

    override fun updatePost(domain: Post): Post =
        postJpaRepository.save(PostEntity.fromDomain(domain)).toDomain()

    // @Async
    override fun incrementViewCount(domain: Post) {
        val result = postJpaRepository.increaseViewCount(domain.id!!, domain.viewCount!!)
        if(result == 0) {
            logger.error("Failed to increase view count for Post ID: ${domain.id}")
            TODO("Retry or logging")
        }
    }

    override fun findAllPosts(): List<Post> =
        postJpaRepository.findAll().map { it.toDomain() }

    override fun findPostById(id: Long): Post? =
        postJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)?.let { return it }

    override fun findByActivePosts(id: Long): Post? {
        postJpaRepository.findActivePostById(id)
            .let { return it?.toDomain() }
    }

    override fun deletePostById(domain: Post): Post =
        postJpaRepository.save(PostEntity.fromDomain(domain)).toDomain()

}