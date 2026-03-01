package com.lucas.stompdemo.service

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.jupiter.api.Test

class ChatRoomServiceTest {
    private val chatRoomService = ChatRoomService()

    @Test
    fun `createRoom stores and returns room`() {
        val room = chatRoomService.createRoom("Lobby", "Lucas")

        assertEquals("Lobby", room.name)
        assertEquals("Lucas", room.creatorName)
        assertEquals(room, chatRoomService.getRoom(room.id))
        assertEquals(1, chatRoomService.getRooms().size)
    }

    @Test
    fun `createRoom rejects blank name`() {
        assertFailsWith<IllegalArgumentException> {
            chatRoomService.createRoom("   ", "Lucas")
        }
    }

    @Test
    fun `createRoom rejects blank creator name`() {
        assertFailsWith<IllegalArgumentException> {
            chatRoomService.createRoom("Lobby", "   ")
        }
    }

    @Test
    fun `deleteRoom removes room when requester is creator`() {
        val room = chatRoomService.createRoom("Lobby", "Lucas")

        chatRoomService.deleteRoom(room.id, "Lucas")

        assertEquals(0, chatRoomService.getRooms().size)
    }

    @Test
    fun `deleteRoom rejects non creator`() {
        val room = chatRoomService.createRoom("Lobby", "Lucas")

        assertFailsWith<IllegalStateException> {
            chatRoomService.deleteRoom(room.id, "Other")
        }
    }

    @Test
    fun `deleteRoom rejects unknown room`() {
        assertFailsWith<NoSuchElementException> {
            chatRoomService.deleteRoom(999L, "Lucas")
        }
    }
}
