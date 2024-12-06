package com.chatservice.chat_api.dto.request

data class ChatApiRequest(
    val questing: String,
    val isStreaming: Boolean = false,
    val model: String = "gpt-3.5-turbo"
)
