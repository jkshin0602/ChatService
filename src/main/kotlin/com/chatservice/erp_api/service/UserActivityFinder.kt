package com.chatservice.erp_api.service

import com.chatservice.common.exception.NotFoundException
import com.chatservice.erp_api.dto.response.ActivityRecordApiResult
import com.chatservice.events.ActivityType
import com.chatservice.events.QActivityEvent.activityEvent
import com.chatservice.user.domain.UserManager
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class UserActivityFinder(
    private val jpaQueryFactory: JPAQueryFactory,
    private val userManager: UserManager,
) {
    fun getActivityInfo(userId: Long): ActivityRecordApiResult {
        val user = userManager.getUser(userId) ?: throw NotFoundException("User not found")
        require(user.isAdmin()) { "User is not admin" }

        val endTime = LocalDateTime.now()
        val startTime = endTime.minusDays(1)

        val results = jpaQueryFactory
            .select(
                activityEvent.event,
                activityEvent.count()
            )
            .from(activityEvent)
            .where(activityEvent.createdDate.between(startTime, endTime))
            .groupBy(activityEvent.event)
            .fetch()
            .associate { tuple ->
                tuple.get(activityEvent.event) to tuple.get(activityEvent.count())
            }

        return ActivityRecordApiResult(
            joinCount = results[ActivityType.JOIN]?.toInt() ?: 0,
            loginCount = results[ActivityType.LOGIN]?.toInt() ?: 0,
            chatCount = results[ActivityType.CHAT]?.toInt() ?: 0
        )
    }
}
