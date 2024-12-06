package com.chatservice.feedback.domain

import com.chatservice.feedback.entity.FeedbackState
import java.time.LocalDateTime

data class FeedbackDomain(
    val feedbackId: Long,
    val createdDate: LocalDateTime,
    val isPositive: Boolean,
    val state: FeedbackState,
)
