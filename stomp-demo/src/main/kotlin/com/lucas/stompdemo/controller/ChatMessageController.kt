package com.lucas.stompdemo.controller

import com.lucas.stompdemo.model.ChatMessage
import com.lucas.stompdemo.model.IncomingChatMessage
import com.lucas.stompdemo.service.ChatMessageService
import com.lucas.stompdemo.service.ChatRoomService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class ChatMessageController(
    private val chatRoomService: ChatRoomService,
    private val chatMessageService: ChatMessageService,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    @MessageMapping("/chat.send/{roomId}")
    fun sendMessage(
        @DestinationVariable roomId: Long,
        @Payload incomingMessage: IncomingChatMessage,
    ) {
        val room = chatRoomService.getRoom(roomId) ?: return
        val outboundMessage: ChatMessage = chatMessageService.createMessage(
            roomId = room.id,
            sender = incomingMessage.sender,
            content = incomingMessage.content,
            type = incomingMessage.type,
        )

        messagingTemplate.convertAndSend("/topic/rooms/${room.id}", outboundMessage)
    }
}
