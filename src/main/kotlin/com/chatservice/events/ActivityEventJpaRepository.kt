package com.chatservice.events

import org.springframework.data.jpa.repository.JpaRepository

interface ActivityEventJpaRepository : JpaRepository<ActivityEvent, Long>
