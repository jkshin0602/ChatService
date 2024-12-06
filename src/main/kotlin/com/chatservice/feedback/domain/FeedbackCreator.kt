package com.chatservice.feedback.domain

import com.chatservice.chat.domain.ChatRepository
import com.chatservice.chat.repository.ThreadJpaRepository
import com.chatservice.common.exception.NotFoundException
import com.chatservice.feedback.entity.FeedbackEntity
import com.chatservice.user.domain.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FeedbackCreator(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository,
    private val feedbackChecker: FeedbackChecker,
    private val threadRepository: ThreadJpaRepository,
    private val feedbackRepository: FeedbackRepository,
) {
    fun create(request: CreateFeedback) {
        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw NotFoundException("user not found")
        val chat = chatRepository.findByIdOrNull(request.chatId)
            ?: throw NotFoundException("chat not found")
        val thread = threadRepository.findByIdOrNull(chat.threadId)
            ?: throw NotFoundException("thread not found")

        require(thread.userId == user.id!!) { "Can only leave feedback in your own conversations" }
        require(!feedbackChecker.existFeedback(user.id!!, chat.id!!)) { "You have already written feedback" }

        FeedbackEntity.create(
            userId = user.id!!,
            chatId = chat.id!!,
            isPositive = request.isPositive,
        ).also {
            feedbackRepository.save(it)
        }
    }
}

data class CreateFeedback(
    val userId: Long,
    val chatId: Long,
    val isPositive: Boolean,
)
