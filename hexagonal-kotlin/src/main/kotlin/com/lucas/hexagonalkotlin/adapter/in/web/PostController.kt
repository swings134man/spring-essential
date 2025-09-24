package com.lucas.hexagonalkotlin.adapter.`in`.web

import com.lucas.hexagonalkotlin.application.post.mapper.PostCommandMapper
import com.lucas.hexagonalkotlin.application.post.port.`in`.PostUseCase
import com.lucas.hexagonalkotlin.domain.post.dto.PostCreateDto
import com.lucas.hexagonalkotlin.domain.post.dto.PostDeleteDto
import com.lucas.hexagonalkotlin.domain.post.dto.PostDto
import com.lucas.hexagonalkotlin.domain.post.dto.PostUpdateDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * PostController.kt: Post RestController
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 25. 오전 12:43
 * @description: 
 */
@RestController
@RequestMapping("/api/post")
class PostController(
    private val postUseCase: PostUseCase,
    private val postMapper: PostCommandMapper
) {

    @PostMapping
    fun createPost(@RequestBody dto: PostCreateDto): ResponseEntity<PostDto> {
        val result = postUseCase.createPost(postMapper.toCreateCommand(dto))
        return ResponseEntity.ok(result)
    }

    @PutMapping
    fun updatePost(@RequestBody dto: PostUpdateDto): ResponseEntity<PostDto> {
        val result = postUseCase.updatePost(postMapper.toUpdateCommand(dto))
        return ResponseEntity.ok(result)
    }

    @GetMapping
    fun findAllPosts(): ResponseEntity<List<PostDto?>?> {
        val result = postUseCase.findAllPosts()
        return ResponseEntity.ok(result)
    }

    @GetMapping("all/{id}")
    fun findPostById(@PathVariable id: Long): ResponseEntity<PostDto?> {
        val result = postUseCase.findPostById(id)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    fun findActivePostById(@PathVariable id: Long): ResponseEntity<PostDto?> {
        val result = postUseCase.findByActivePosts(id)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/delete")
    fun deletePostById(@RequestBody dto: PostDeleteDto): ResponseEntity<String?> {
        postUseCase.deletePostById(postMapper.toDeleteCommand(dto))
        return ResponseEntity.ok("Post deleted successfully")
    }



}