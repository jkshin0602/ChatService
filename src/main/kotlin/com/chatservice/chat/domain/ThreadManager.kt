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
    private val threadRepository: ThreadRepository,
) {
    fun getAllChat(
        userId: Long,
        pageable: Pageable,
    ): QueryChatApiResponses {
        val user = userManager.getUser(userId) ?: throw NotFoundException("User not found")
        val result = chattingFinderMap[user.role]!!.getAllChat(user.id!!, pageable)
        return QueryChatApiResponses(result)
    }

    fun deleteThread(
        threadId: Long,
        userId: Long,
    ){
        val user = userManager.getUser(userId) ?: throw NotFoundException("User not found")
        val thread = threadRepository.findByIdOrNull(threadId) ?: throw NotFoundException("Thread not found")

        require(thread.userId == user.id!!) { "Can only delete your own thread" }

        threadRepository.deleteById(threadId)
    }
}
