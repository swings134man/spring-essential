package com.lucas.stompdemo.service

import com.lucas.stompdemo.model.MessageType
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.jupiter.api.Test

class ChatMessageServiceTest {
    private val chatMessageService = ChatMessageService()

    @Test
    fun `create talk message with trimmed content`() {
        val message = chatMessageService.createMessage(
            roomId = 1L,
            sender = " Lucas ",
            content = " hello ",
            type = MessageType.TALK,
        )

        assertEquals("Lucas", message.sender)
        assertEquals("hello", message.content)
        assertEquals(MessageType.TALK, message.type)
    }

    @Test
    fun `create enter message generates system content`() {
        val message = chatMessageService.createMessage(
            roomId = 1L,
            sender = "Lucas",
            content = "",
            type = MessageType.ENTER,
        )

        assertEquals("Lucas joined the room.", message.content)
    }

    @Test
    fun `create talk message rejects blank content`() {
        assertFailsWith<IllegalArgumentException> {
            chatMessageService.createMessage(
                roomId = 1L,
                sender = "Lucas",
                content = "   ",
                type = MessageType.TALK,
            )
        }
    }
}
