package com.lucas.hexagonalkotlin.application.post.port.`in`

import com.lucas.hexagonalkotlin.application.post.commands.PostCommand
import com.lucas.hexagonalkotlin.domain.post.dto.PostDto

/**
 * PostUseCase.kt: Post UseCase Port(in)
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 25. 오전 12:59
 * @description: 
 */
interface PostUseCase {

    fun createPost(command: PostCommand.CreatePostCommand): PostDto
    fun updatePost(command: PostCommand.UpdatePostCommand): PostDto
    fun findAllPosts(): List<PostDto?>
    fun findPostById(id: Long): PostDto?
    fun findByActivePosts(id: Long): PostDto?
    fun deletePostById(command: PostCommand.DeletePostCommand)

}