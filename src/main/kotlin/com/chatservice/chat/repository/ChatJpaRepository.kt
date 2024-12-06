package com.chatservice.chat.repository

import com.chatservice.chat.domain.ChatRepository
import com.chatservice.chat.entity.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatJpaRepository : JpaRepository<ChatEntity, Long>, ChatRepository {
    @Query("select c from ChatEntity c where c.id = :id")
    override fun findByIdOrNull(id: Long): ChatEntity?
}
