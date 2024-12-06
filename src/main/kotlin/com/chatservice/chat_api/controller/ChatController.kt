package com.chatservice.chat_api.controller

import com.chatservice.chat_api.dto.request.ChatApiRequest
import com.chatservice.chat_api.dto.response.ChatApiResponse
import com.chatservice.chat_api.service.ChatService
import com.chatservice.common.response.ApiResult
import com.chatservice.user.domain.AuthUser
import com.chatservice.user.domain.AuthenticatedUser
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/chat")
@RestController
class ChatController(
    private val chatService: ChatService,
) {
    @PostMapping
    fun sendMessage(
        @AuthUser user: AuthenticatedUser,
        @RequestBody request: ChatApiRequest,
    ): ApiResult<ChatApiResponse> {
        val result = chatService.sendMessage(
            userId = user.id,
            request = request
        )
        return ApiResult(data = result)
    }
}
