package com.lucas.hexagonalkotlin.application.post.port.out

import com.lucas.hexagonalkotlin.domain.post.model.Post

/**
 * PostRepository.kt: Post port out Repository
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 25. 오전 12:44
 * @description: 
 */
interface PostRepository {

    fun createPost(domain: Post): Post
    fun updatePost(domain: Post): Post
    fun incrementViewCount(domain: Post)
    fun findAllPosts(): List<Post>
    fun findPostById(id: Long): Post?
    fun findByActivePosts(id: Long): Post?
    fun deletePostById(domain: Post): Post

}