package com.lucas.hexagonalkotlin.application.post.mapper

import com.lucas.hexagonalkotlin.application.post.commands.PostCommand
import com.lucas.hexagonalkotlin.domain.post.dto.PostCreateDto
import com.lucas.hexagonalkotlin.domain.post.dto.PostDeleteDto
import com.lucas.hexagonalkotlin.domain.post.dto.PostUpdateDto
import com.lucas.hexagonalkotlin.domain.post.model.Post
import org.mapstruct.Mapper
import org.mapstruct.Mapping

/**
 * PostCommandMapper.kt: Controller DTO -> Command Object Mapper
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 25. 오전 12:56
 * @description:
 */
@Mapper(componentModel = "spring")
interface PostCommandMapper {

    // dto -> Command obj
    @Mapping(target = "delYn", constant = "N")
    @Mapping(target = "viewCount", constant = "0")
    fun toCreateCommand(dto: PostCreateDto): PostCommand.CreatePostCommand
    fun toUpdateCommand(dto: PostUpdateDto): PostCommand.UpdatePostCommand
    fun toDeleteCommand(dto: PostDeleteDto): PostCommand.DeletePostCommand

    // Command obj -> Domain
    fun toDomain(command: PostCommand.CreatePostCommand): Post
    fun toDomain(command: PostCommand.UpdatePostCommand): Post
    fun toDomain(command: PostCommand.DeletePostCommand): Post

}