package com.lucas.hexagonalkotlin.application.post.service

import com.lucas.hexagonalkotlin.application.post.commands.PostCommand
import com.lucas.hexagonalkotlin.application.post.mapper.PostCommandMapper
import com.lucas.hexagonalkotlin.application.post.port.`in`.PostUseCase
import com.lucas.hexagonalkotlin.application.post.port.out.PostRepository
import com.lucas.hexagonalkotlin.application.users.port.out.UsersRepository
import com.lucas.hexagonalkotlin.domain.post.PostEventDto
import com.lucas.hexagonalkotlin.domain.post.dto.PostDto
import com.lucas.hexagonalkotlin.domain.post.model.Post
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * PostService.kt: Post Service
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 25. 오전 12:45
 * @description:
 */
@Service
class PostService(
    private val postMapper: PostCommandMapper,
    private val postRepository: PostRepository,
    private val userRepository: UsersRepository,
    private val eventPublisher: ApplicationEventPublisher
): PostUseCase {

    @Transactional
    override fun createPost(command: PostCommand.CreatePostCommand): PostDto {
        // 1. user 존재 여부 체크
        val user = userRepository.findUserById(command.userId)
            ?: throw RuntimeException("User not found with id: ${command.userId}")

        // 2. post 생성
        val post = postMapper.toDomain(command)
            .apply { this.writer = user.email }

        // 3. post 저장
        return postRepository.createPost(post)
            .let { PostDto.fromDomain(it) }
    }

    @Transactional
    override fun updatePost(command: PostCommand.UpdatePostCommand): PostDto {
        // 1. domain get
        val domain = postRepository.findPostById(command.id)
            ?: throw RuntimeException("Post not found with id: ${command.id}")

        // 2. mapping - domain update function
        domain.updatePost(command.title, command.content, command.userId)

        // 3. save and return dto
        return postRepository.updatePost(domain)
            .let { PostDto.fromDomain(it) }
    }

    @Transactional(readOnly = true)
    override fun findAllPosts(): List<PostDto?> =
        postRepository.findAllPosts().map { it.let { PostDto.fromDomain(it) } }

    @Transactional
    override fun findPostById(id: Long): PostDto? {
        val domain = postRepository.findPostById(id)
            ?: throw RuntimeException("Post not found with id: $id")

//        domain.incrementViewCount()
//        postRepository.incrementViewCount(domain) // TODO("Async 처리")

        return PostDto.fromDomain(domain)
    }

    @Transactional(readOnly = true)
    override fun findByActivePosts(id: Long): PostDto? {
        val domain = postRepository.findByActivePosts(id)
            ?: throw RuntimeException("Active Post not found with id: $id")

        domain.incrementViewCount()

        eventPublisher.publishEvent(PostEventDto.PostViewIncrementEventDto.fromDomain(domain))

        return PostDto.fromDomain(domain)
    }

    @Transactional
    override fun deletePostById(command: PostCommand.DeletePostCommand) {
        // 1. 조회
        val domain = postRepository.findPostById(command.id!!)
            ?: throw RuntimeException("Post not found with id: ${command.id}")

        // 2. 삭제 처리
        domain.markAsDeleted()

        val result = postRepository.deletePostById(domain)
        if(result.delYn == "N") throw RuntimeException("Failed to delete post with id: ${command.id}")
    }
}