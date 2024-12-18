package com.chatservice.chat_api.service

import com.chatservice.chat.domain.ChatManager
import com.chatservice.chat.domain.ChatRequest
import com.chatservice.chat.domain.OpenAIModel
import com.chatservice.chat_api.dto.request.ChatApiRequest
import com.chatservice.chat_api.dto.response.ChatApiResponse
import com.chatservice.events.ActivityType
import com.chatservice.events.UserActivityEventDispatcher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class SendChatService(
    private val chatManager: ChatManager,
    private val userActivityEventDispatcher: UserActivityEventDispatcher,
) {
    fun sendMessage(
        userId: Long,
        request: ChatApiRequest,
    ): ChatApiResponse {
        val requestInfo = ChatRequest(
            question = request.questing,
            isStreaming = request.isStreaming,
            model = OpenAIModel.getByDescription(request.model),
            userId = userId
        )
        val result = chatManager.sendMessage(requestInfo)

        return ChatApiResponse(result.answer).also {
            userActivityEventDispatcher.publish(ActivityType.CHAT)
        }
    }
}
