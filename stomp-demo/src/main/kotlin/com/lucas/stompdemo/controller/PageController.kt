package com.lucas.stompdemo.controller

import com.lucas.stompdemo.service.ChatRoomService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class PageController(
    private val chatRoomService: ChatRoomService,
) {
    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("rooms", chatRoomService.getRooms())
        return "index"
    }

    @GetMapping("/rooms/{roomId}")
    fun chatRoomPage(
        @PathVariable roomId: Long,
        @RequestParam username: String?,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): String {
        val room = chatRoomService.getRoom(roomId)
        val normalizedUsername = username?.trim().orEmpty()

        if (room == null || normalizedUsername.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효한 채팅방과 사용자 이름이 필요합니다.")
            return "redirect:/"
        }

        model.addAttribute("room", room)
        model.addAttribute("username", normalizedUsername)
        return "chat-room"
    }
}
