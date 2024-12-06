package com.chatservice.feedback_api.controller

import com.chatservice.common.response.ApiResult
import com.chatservice.feedback_api.dto.request.FeedbackApiRequest
import com.chatservice.feedback_api.service.CreateFeedbackService
import com.chatservice.user.domain.AuthUser
import com.chatservice.user.domain.AuthenticatedUser
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/feedback")
@RestController
class FeedbackController(
    private val createFeedbackService: CreateFeedbackService,
) {
    @PostMapping
    fun createFeedback(
        @AuthUser user: AuthenticatedUser,
        @RequestBody request: FeedbackApiRequest,
    ): ApiResult<Nothing> {
        createFeedbackService.createFeedback(user.id, request)
        return ApiResult()
    }
}
