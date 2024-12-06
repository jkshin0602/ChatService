package com.chatservice.chat.domain

import com.chatservice.chat_api.service.QueryChatApiResponses
import com.chatservice.common.exception.NotFoundException
import com.chatservice.user.domain.UserManager
import com.chatservice.user.entity.UserRole
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ThreadManager(
    private val userManager: UserManager,
    private val chattingFinderMap: Map<UserRole, ChattingFinder>,
) {
    fun getAllChat(
        userId: Long,
        pageable: Pageable,
    ): QueryChatApiResponses {
        val user = userManager.getUser(userId) ?: throw NotFoundException("User not found")
        val result = chattingFinderMap[user.role]!!.getAllChat(user.id!!, pageable)
        return QueryChatApiResponses(result)
    }
}
