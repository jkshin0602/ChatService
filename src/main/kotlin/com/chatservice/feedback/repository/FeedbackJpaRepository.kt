package com.chatservice.feedback.repository

import com.chatservice.feedback.domain.FeedbackRepository
import com.chatservice.feedback.entity.FeedbackEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FeedbackJpaRepository : JpaRepository<FeedbackEntity, Long>, FeedbackRepository {
    @Query("select f from FeedbackEntity f where f.id = :feedbackId")
    override fun findByIdOrNull(feedbackId: Long): FeedbackEntity?
}
