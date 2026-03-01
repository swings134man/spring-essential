package com.lucas.stompdemo.controller

import com.lucas.stompdemo.model.ChatRoom
import com.lucas.stompdemo.model.ChatMessage
import com.lucas.stompdemo.model.CreateRoomRequest
import com.lucas.stompdemo.model.DeleteRoomRequest
import com.lucas.stompdemo.model.MessageType
import com.lucas.stompdemo.service.ChatRoomService
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/rooms")
class ChatRoomRestController(
    private val chatRoomService: ChatRoomService,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @GetMapping
    fun getRooms(): List<ChatRoom> = chatRoomService.getRooms()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createRoom(@RequestBody request: CreateRoomRequest): ChatRoom =
        chatRoomService.createRoom(request.name, request.creatorName)

    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRoom(
        @PathVariable roomId: Long,
        @RequestBody request: DeleteRoomRequest,
    ) {
        val room = chatRoomService.getRoom(roomId) ?: throw NoSuchElementException("Chat room not found.")
        val normalizedRequesterName = request.requesterName.trim()
        require(normalizedRequesterName.isNotBlank()) { "Requester name must not be blank." }
        check(room.creatorName == normalizedRequesterName) { "Only the creator can delete this room." }

        messagingTemplate.convertAndSend(
            "/topic/rooms/${room.id}",
            ChatMessage(
                roomId = room.id,
                sender = normalizedRequesterName,
                content = "채팅방이 삭제되었습니다. 메인 페이지로 이동합니다.",
                type = MessageType.ROOM_DELETED,
                timestamp = LocalDateTime.now(),
            ),
        )

        chatRoomService.deleteRoom(roomId, request.requesterName)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleIllegalArgumentException(exception: IllegalArgumentException): Map<String, String> =
        mapOf("message" to (exception.message ?: "Invalid request."))

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleNoSuchElementException(exception: NoSuchElementException): Map<String, String> =
        mapOf("message" to (exception.message ?: "Resource not found."))

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun handleIllegalStateException(exception: IllegalStateException): Map<String, String> =
        mapOf("message" to (exception.message ?: "Forbidden."))
}
