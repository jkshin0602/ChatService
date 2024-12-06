package com.chatservice.chat_api.service

import com.chatservice.chat.domain.ChatManager
import com.chatservice.chat.domain.ChatRequest
import com.chatservice.chat.domain.OpenAIModel
import com.chatservice.chat_api.dto.request.ChatApiRequest
import com.chatservice.chat_api.dto.response.ChatApiResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ChatService(
    private val chatManager: ChatManager,
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

        return ChatApiResponse(result.answer)
    }
}
