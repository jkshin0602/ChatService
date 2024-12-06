package com.chatservice.chat.domain

import com.chatservice.chat.entity.ThreadEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PageThreadRepository {
    fun findAllByUserId(pageable: Pageable, userId: Long): Page<ThreadEntity>
    fun findAllById(pageable: Pageable): Page<ThreadEntity>
}
