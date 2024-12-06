package com.chatservice.chat_api.controller

import com.chatservice.chat_api.dto.request.ChatApiRequest
import com.chatservice.chat_api.dto.request.DeleteApiRequest
import com.chatservice.chat_api.dto.response.ChatApiResponse
import com.chatservice.chat_api.service.DeleteThreadService
import com.chatservice.chat_api.service.QueryChatApiResponses
import com.chatservice.chat_api.service.QueryChatService
import com.chatservice.chat_api.service.SendChatService
import com.chatservice.common.response.ApiResult
import com.chatservice.user.domain.AuthUser
import com.chatservice.user.domain.AuthenticatedUser
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/chat")
@RestController
class ChatController(
    private val sendChatService: SendChatService,
    private val queryChatService: QueryChatService,
    private val deleteThreadService: DeleteThreadService,
) {
    @PostMapping
    fun sendMessage(
        @AuthUser user: AuthenticatedUser,
        @RequestBody request: ChatApiRequest,
    ): ApiResult<ChatApiResponse> {
        val result = sendChatService.sendMessage(
            userId = user.id,
            request = request
        )
        return ApiResult(data = result)
    }

    @GetMapping
    fun getMessages(
        @AuthUser user: AuthenticatedUser,
        @PageableDefault(
            size = 10,
            sort = ["createdDate"],
            direction = Sort.Direction.ASC,
        ) pageable: Pageable,
    ): ApiResult<QueryChatApiResponses> {
        val result = queryChatService.getChats(user.id, pageable)
        return ApiResult(data = result)
    }

    @DeleteMapping
    fun deleteThread(
        @AuthUser user: AuthenticatedUser,
        @RequestBody request: DeleteApiRequest,
    ): ApiResult<Nothing> {
        deleteThreadService.deleteChat(user.id, request)
        return ApiResult()
    }
}
