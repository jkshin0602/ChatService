package com.chatservice.feedback.domain

import com.chatservice.feedback.entity.FeedbackEntity

interface FeedbackRepository {
    fun save(feedbackEntity: FeedbackEntity): FeedbackEntity
    fun findByIdOrNull(feedbackId: Long): FeedbackEntity?
}
