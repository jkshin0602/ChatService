package com.chatservice.feedback.domain

import com.chatservice.chat.entity.QThreadEntity.threadEntity
import com.chatservice.feedback.entity.QFeedbackEntity.feedbackEntity
import com.chatservice.user.entity.UserRole
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

interface FeedbackFinder {
    fun getFeedBacks(request: FeedbackRequest): Page<FeedbackDomain>
}

@Component("MemberFeedbackFinder")
class MemberFeedbackFinder(
    private val jpaQueryFactory: JPAQueryFactory,
) : FeedbackFinder {
    override fun getFeedBacks(request: FeedbackRequest): Page<FeedbackDomain> {
        val query = jpaQueryFactory.selectFrom(feedbackEntity)
            .where(
                feedbackEntity.userId.eq(request.userId),
                request.isPositive?.let { feedbackEntity.isPositive.eq(it) }
            )

        request.pageable.sort.forEach { order ->
            when (order.property) {
                "createdDate" -> {
                    if (order.isAscending) {
                        query.orderBy(threadEntity.createdDate.asc())
                    } else {
                        query.orderBy(threadEntity.createdDate.desc())
                    }
                }
            }
        }

        val content = query
            .offset(request.pageable.offset)
            .limit(request.pageable.pageSize.toLong())
            .fetch().map {
                FeedbackDomain(
                    feedbackId = it.id!!,
                    createdDate = it.createdDate!!,
                    isPositive = it.isPositive,
                    state = it.state
                )
            }

        val total = jpaQueryFactory.select(feedbackEntity.count())
            .from(feedbackEntity)
            .where(
                feedbackEntity.userId.eq(request.userId),
                request.isPositive?.let { feedbackEntity.isPositive.eq(it) }
            )
            .fetchOne() ?: 0L

        return PageImpl(content, request.pageable, total)
    }
}

@Component("AdminFeedbackFinder")
class AdminFeedbackFinder(
    private val jpaQueryFactory: JPAQueryFactory,
) : FeedbackFinder {
    override fun getFeedBacks(request: FeedbackRequest): Page<FeedbackDomain> {
        val query = jpaQueryFactory.selectFrom(feedbackEntity)
            .where(request.isPositive?.let { feedbackEntity.isPositive.eq(it) })

        request.pageable.sort.forEach { order ->
            when (order.property) {
                "createdDate" -> {
                    if (order.isAscending) {
                        query.orderBy(threadEntity.createdDate.asc())
                    } else {
                        query.orderBy(threadEntity.createdDate.desc())
                    }
                }
            }
        }

        val content = query
            .offset(request.pageable.offset)
            .limit(request.pageable.pageSize.toLong())
            .fetch().map {
                FeedbackDomain(
                    feedbackId = it.id!!,
                    createdDate = it.createdDate!!,
                    isPositive = it.isPositive,
                    state = it.state
                )
            }

        val total = jpaQueryFactory.select(feedbackEntity.count())
            .from(feedbackEntity)
            .where(request.isPositive?.let { feedbackEntity.isPositive.eq(it) })
            .fetchOne() ?: 0L

        return PageImpl(content, request.pageable, total)
    }
}

data class FeedbackRequest(
    val userId: Long,
    val isPositive: Boolean?,
    val pageable: Pageable,
)

@Configuration
class FeedbackFinderConfig {
    @Bean
    fun feedbackFinderMap(
        memberChattingFinder: MemberFeedbackFinder,
        adminChattingFinder: AdminFeedbackFinder,
    ): Map<UserRole, FeedbackFinder> {
        return mapOf(
            UserRole.MEMBER to memberChattingFinder,
            UserRole.ADMIN to adminChattingFinder
        )
    }
}
