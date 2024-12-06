package com.chatservice.feedback_api.dto.request

data class FeedbackApiRequest(
    val chatId: Long,
    val isPositive: Boolean,
)
