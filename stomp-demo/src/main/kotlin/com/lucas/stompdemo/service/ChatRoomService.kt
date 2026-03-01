package com.lucas.stompdemo.service

import com.lucas.stompdemo.model.ChatRoom
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class ChatRoomService {
    private val roomIdSequence = AtomicLong(0)
    private val rooms = ConcurrentHashMap<Long, ChatRoom>()

    fun createRoom(name: String, creatorName: String): ChatRoom {
        val normalizedName = name.trim()
        val normalizedCreatorName = creatorName.trim()

        require(normalizedName.isNotBlank()) { "Room name must not be blank." }
        require(normalizedCreatorName.isNotBlank()) { "Creator name must not be blank." }

        val room = ChatRoom(
            id = roomIdSequence.incrementAndGet(),
            name = normalizedName,
            creatorName = normalizedCreatorName,
            createdAt = LocalDateTime.now(),
        )
        rooms[room.id] = room
        return room
    }

    fun getRooms(): List<ChatRoom> = rooms.values.sortedBy { it.id }

    fun getRoom(roomId: Long): ChatRoom? = rooms[roomId]

    fun deleteRoom(roomId: Long, requesterName: String) {
        val room = rooms[roomId] ?: throw NoSuchElementException("Chat room not found.")
        val normalizedRequesterName = requesterName.trim()

        require(normalizedRequesterName.isNotBlank()) { "Requester name must not be blank." }
        check(room.creatorName == normalizedRequesterName) { "Only the creator can delete this room." }

        rooms.remove(roomId)
    }
}
