package com.chatservice.feedback.repository

import com.chatservice.feedback.domain.FeedbackRepository
import com.chatservice.feedback.entity.FeedbackEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FeedbackJpaRepository : JpaRepository<FeedbackEntity, Long>, FeedbackRepository
