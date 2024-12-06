package com.chatservice.feedback_api.controller

import com.chatservice.common.response.ApiResult
import com.chatservice.feedback_api.dto.request.ModifyFeedbackStateApiRequest
import com.chatservice.feedback_api.service.ModifyFeedbackService
import com.chatservice.user.domain.AuthUser
import com.chatservice.user.domain.AuthenticatedUser
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/admin/feedback")
@RestController
class FeedbackAdminController(
    private val modifyFeedbackService: ModifyFeedbackService,
) {
    @PatchMapping("/{feedbackId}")
    fun updateState(
        @AuthUser user: AuthenticatedUser,
        @PathVariable("feedbackId") feedbackId: Long,
        @RequestBody request: ModifyFeedbackStateApiRequest,
    ): ApiResult<Nothing> {
        modifyFeedbackService.updateState(user.id, feedbackId, request)
        return ApiResult()
    }
}
