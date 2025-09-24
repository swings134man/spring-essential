package com.lucas.hexagonalkotlin.application.post.commands

sealed class PostCommand {

    data class CreatePostCommand(
        val title: String,
        val content: String,
        val userId: Long,
        val delYn: String = "N",
        val viewCount: Int = 0
    ) : PostCommand()

    data class UpdatePostCommand(
        val id: Long,
        val title: String,
        val content: String,
        val userId: Long
    ) : PostCommand()

    data class DeletePostCommand(
        val id: Long,
        val userId: Long
    ) : PostCommand()

}