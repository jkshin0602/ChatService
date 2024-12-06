package com.chatservice.common.jpa_audit

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity(
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    var createdDate: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "last_modified_date")
    var lastModifiedDate: LocalDateTime? = null,
)
