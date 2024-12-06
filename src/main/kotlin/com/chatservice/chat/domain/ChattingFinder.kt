package com.chatservice.chat.domain

import com.chatservice.user.entity.UserRole
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

interface ChattingFinder {
    fun getAllChat(
        userId: Long,
        pageable: Pageable,
    ): Page<ThreadDomain>
}

@Component("userChattingFinder")
class MemberChattingFinder(
    private val chatRepository: ChatRepository,
    private val pageThreadRepository: PageThreadRepository,
) : ChattingFinder {
    override fun getAllChat(
        userId: Long,
        pageable: Pageable,
    ): Page<ThreadDomain> {
        val threads = pageThreadRepository.findAllByUserId(pageable, userId)
        val chats = chatRepository.findAllByThreadIdIn(threads.content.map { it.id!! })

        val threadToChatMaps = chats.groupBy { it.threadId }

        val threadDomains = threads.content.map {
            ThreadDomain.create(
                userId = it.userId,
                threadId = it.id!!,
                chats = threadToChatMaps[it.id]!!.sortedBy { it.createdDate },
            )
        }

        return PageImpl(threadDomains, pageable, threads.size.toLong())
    }
}

@Component("adminChattingFinder")
class AdminChattingFinder(
    private val pageThreadRepository: PageThreadRepository,
    private val chatRepository: ChatRepository,
) : ChattingFinder {
    override fun getAllChat(
        userId: Long,
        pageable: Pageable,
    ): Page<ThreadDomain> {
        val threads = pageThreadRepository.findAllById(pageable)
        val chats = chatRepository.findAllByThreadIdIn(threads.content.map { it.id!! })

        val threadToChatMaps = chats.groupBy { it.threadId }

        val threadDomains = threads.content.map {
            ThreadDomain.create(
                userId = it.userId,
                threadId = it.id!!,
                chats = threadToChatMaps[it.id]!!.sortedBy { it.createdDate },
            )
        }

        return PageImpl(threadDomains, pageable, threads.size.toLong())
    }
}

@Configuration
class ChattingFinderConfig {
    @Bean
    fun chattingFinderMap(
        memberChattingFinder: MemberChattingFinder,
        adminChattingFinder: AdminChattingFinder,
    ): Map<UserRole, ChattingFinder> {
        return mapOf(
            UserRole.MEMBER to memberChattingFinder,
            UserRole.ADMIN to adminChattingFinder
        )
    }
}
