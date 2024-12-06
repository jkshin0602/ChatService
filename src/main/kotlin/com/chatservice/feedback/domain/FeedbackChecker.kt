package com.chatservice.feedback.domain

import com.chatservice.feedback.entity.QFeedbackEntity.feedbackEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class FeedbackChecker(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun existFeedback(userId: Long, chatId: Long): Boolean {
        return jpaQueryFactory.selectFrom(feedbackEntity)
            .where(
                feedbackEntity.userId.eq(userId),
                feedbackEntity.chatId.eq(chatId)
            ).fetchFirst() != null
    }

}
