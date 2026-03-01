package com.lucas.stompdemo.model

import java.time.LocalDateTime

data class ChatRoom(
    val id: Long,
    val name: String,
    val creatorName: String,
    val createdAt: LocalDateTime,
)
