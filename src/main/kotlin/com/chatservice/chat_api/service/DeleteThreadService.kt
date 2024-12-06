package com.chatservice.chat_api.service

import com.chatservice.chat.domain.ThreadManager
import com.chatservice.chat_api.dto.request.DeleteApiRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class DeleteThreadService(
    private val threadManager: ThreadManager,
) {
    fun deleteChat(
        userId: Long,
        request: DeleteApiRequest,
    ) {
        threadManager.deleteThread(request.threadId, userId)
    }
}
