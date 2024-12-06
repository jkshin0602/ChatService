package com.chatservice.feedback_api.dto.response

import com.chatservice.feedback.domain.FeedbackDomain
import org.springframework.data.domain.Page

data class FeedbackApiResponses(
    val feedbacks: Page<FeedbackDomain>,
)
