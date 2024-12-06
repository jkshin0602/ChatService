package com.chatservice.feedback_api.service

import com.chatservice.common.exception.NotFoundException
import com.chatservice.feedback.domain.FeedbackFinder
import com.chatservice.feedback.domain.FeedbackRequest
import com.chatservice.feedback_api.dto.response.FeedbackApiResponses
import com.chatservice.user.domain.UserManager
import com.chatservice.user.entity.UserRole
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class QueryFeedbackService(
    private val feedbackFinderMap: Map<UserRole, FeedbackFinder>,
    private val userManager: UserManager,
) {
    fun getFeedbacks(
        userId: Long,
        isPositive: Boolean?,
        pageable: Pageable,
    ): FeedbackApiResponses {
        val user = userManager.getUser(userId) ?: throw NotFoundException("User not found")
        val request = FeedbackRequest(userId = userId, isPositive = isPositive, pageable = pageable)
        val result = feedbackFinderMap[user.role]!!.getFeedBacks(request)
        return FeedbackApiResponses(result)
    }
}
