package com.lucas.stompdemo.model

data class IncomingChatMessage(
    val sender: String,
    val content: String = "",
    val type: MessageType = MessageType.TALK,
)
