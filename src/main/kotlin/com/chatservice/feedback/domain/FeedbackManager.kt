package com.chatservice.feedback.domain

import com.chatservice.common.exception.NotFoundException
import com.chatservice.feedback.entity.FeedbackState
import org.springframework.stereotype.Component

@Component
class FeedbackManager(
    private val feedbackRepository: FeedbackRepository,
) {
    fun updateState(feedbackId: Long, state: FeedbackState) {
        val feedback = feedbackRepository.findByIdOrNull(feedbackId) ?: throw NotFoundException("feedback not found")
        feedback.updateState(state)
    }
}
