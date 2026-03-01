package com.lucas.stompdemo.model

import java.time.LocalDateTime

data class ChatMessage(
    val roomId: Long,
    val sender: String,
    val content: String,
    val type: MessageType,
    val timestamp: LocalDateTime,
)
