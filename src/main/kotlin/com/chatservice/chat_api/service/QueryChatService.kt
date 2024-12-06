package com.chatservice.chat_api.service

import com.chatservice.chat.domain.ThreadDomain
import com.chatservice.chat.domain.ThreadManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class QueryChatService(
    private val threadManager: ThreadManager,
) {
    fun getChats(userId: Long, pageable: Pageable): QueryChatApiResponses {
        return threadManager.getAllChat(userId, pageable)
    }
}

data class QueryChatApiResponses(
    val chatInfos: Page<ThreadDomain>,
)
