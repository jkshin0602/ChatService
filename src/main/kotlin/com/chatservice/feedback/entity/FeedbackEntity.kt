package com.chatservice.feedback.entity

import com.chatservice.common.jpa_audit.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Table(name = "feedbacks")
@Entity
class FeedbackEntity(
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "chatId", nullable = false)
    val chatId: Long,
    @Column(name = "isPositive", nullable = false)
    val isPositive: Boolean,
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    val state: FeedbackState,
) : BaseTimeEntity() {
    companion object {
        fun create(
            userId: Long,
            chatId: Long,
            isPositive: Boolean,
        ) = FeedbackEntity(
            userId = userId,
            chatId = chatId,
            isPositive = isPositive,
            state = FeedbackState.PENDING,
        )
    }
}

enum class FeedbackState {
    PENDING, RESOLVED,
    ;
}
