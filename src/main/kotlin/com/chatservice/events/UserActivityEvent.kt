package com.chatservice.events

import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Transactional(propagation = Propagation.MANDATORY)
@Component
class UserActivityEventDispatcher(
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun publish(eventType: ActivityType) {
        eventPublisher.publishEvent(UserActivityEvent(eventType))
    }
}

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Component
class UserActivityEventListener(
    private val activityEventJpaRepository: ActivityEventJpaRepository,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun listen(event: UserActivityEvent) {
        activityEventJpaRepository.save(ActivityEvent(event.eventType))
    }
}

data class UserActivityEvent(val eventType: ActivityType)
