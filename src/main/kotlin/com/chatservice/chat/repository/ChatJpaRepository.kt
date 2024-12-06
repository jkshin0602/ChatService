package com.chatservice.chat.repository

import com.chatservice.chat.domain.ChatRepository
import com.chatservice.chat.entity.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatJpaRepository : JpaRepository<ChatEntity, Long>, ChatRepository
