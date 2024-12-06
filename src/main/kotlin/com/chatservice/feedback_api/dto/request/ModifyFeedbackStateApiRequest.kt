package com.chatservice.feedback_api.dto.request

import com.chatservice.feedback.entity.FeedbackState

data class ModifyFeedbackStateApiRequest(
    val state: FeedbackState,
)
