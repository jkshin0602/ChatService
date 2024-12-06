package com.chatservice.events

import com.chatservice.common.jpa_audit.BaseTimeEntity
import jakarta.persistence.Entity

@Entity
class ActivityEvent(
    val event: ActivityType,
) : BaseTimeEntity()

enum class ActivityType {
    JOIN, LOGIN, CHAT,
    ;
}
