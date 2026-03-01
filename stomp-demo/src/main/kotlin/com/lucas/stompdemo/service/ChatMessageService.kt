package com.lucas.stompdemo.service

import com.lucas.stompdemo.model.ChatMessage
import com.lucas.stompdemo.model.MessageType
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatMessageService {
    fun createMessage(
        roomId: Long,
        sender: String,
        content: String,
        type: MessageType,
    ): ChatMessage {
        val normalizedSender = sender.trim()
        require(normalizedSender.isNotBlank()) { "Sender must not be blank." }

        val normalizedContent = when (type) {
            MessageType.TALK -> content.trim().also {
                require(it.isNotBlank()) { "Message content must not be blank." }
            }

            MessageType.ENTER -> "${normalizedSender} joined the room."
            MessageType.LEAVE -> "${normalizedSender} left the room."
            MessageType.ROOM_DELETED -> content.trim().ifBlank { "This room has been deleted." }
        }

        return ChatMessage(
            roomId = roomId,
            sender = normalizedSender,
            content = normalizedContent,
            type = type,
            timestamp = LocalDateTime.now(),
        )
    }
}
