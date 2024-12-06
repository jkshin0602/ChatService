package com.chatservice.feedback_api.service

import com.chatservice.feedback.domain.CreateFeedback
import com.chatservice.feedback.domain.FeedbackCreator
import com.chatservice.feedback_api.dto.request.FeedbackApiRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CreateFeedbackService(
    private val feedbackCreator: FeedbackCreator,
) {
    fun createFeedback(
        userId: Long,
        request: FeedbackApiRequest,
    ) {
        val createRequest = CreateFeedback(
            userId = userId,
            chatId = request.chatId,
            isPositive = request.isPositive,
        )
        feedbackCreator.create(createRequest)
    }
}
