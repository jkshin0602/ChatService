package com.chatservice.feedback_api.service

import com.chatservice.common.exception.NotFoundException
import com.chatservice.feedback.domain.FeedbackManager
import com.chatservice.feedback_api.dto.request.ModifyFeedbackStateApiRequest
import com.chatservice.user.domain.UserManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ModifyFeedbackService(
    private val feedbackManager: FeedbackManager,
    private val userManager: UserManager,
) {
    fun updateState(id: Long, feedbackId: Long, request: ModifyFeedbackStateApiRequest) {
        val user = userManager.getUser(id) ?: throw NotFoundException("User not found")

        require(user.isAdmin()) { "User is not admin" }

        feedbackManager.updateState(feedbackId, request.state)
    }
}
