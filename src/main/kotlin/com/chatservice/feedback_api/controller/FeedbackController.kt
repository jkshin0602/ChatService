package com.chatservice.feedback_api.controller

import com.chatservice.common.response.ApiResult
import com.chatservice.feedback_api.dto.request.FeedbackApiRequest
import com.chatservice.feedback_api.dto.response.FeedbackApiResponses
import com.chatservice.feedback_api.service.CreateFeedbackService
import com.chatservice.feedback_api.service.QueryFeedbackService
import com.chatservice.user.domain.AuthUser
import com.chatservice.user.domain.AuthenticatedUser
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/feedback")
@RestController
class FeedbackController(
    private val createFeedbackService: CreateFeedbackService,
    private val queryFeedbackService: QueryFeedbackService,
) {
    @PostMapping
    fun createFeedback(
        @AuthUser user: AuthenticatedUser,
        @RequestBody request: FeedbackApiRequest,
    ): ApiResult<Nothing> {
        createFeedbackService.createFeedback(user.id, request)
        return ApiResult()
    }

    @GetMapping
    fun getFeedbacks(
        @AuthUser user: AuthenticatedUser,
        @RequestParam(required = false) isPositive: Boolean?,
        @PageableDefault(
            size = 10,
            sort = ["createdDate"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable,
    ): ApiResult<FeedbackApiResponses> {
        val result = queryFeedbackService.getFeedbacks(
            userId = user.id,
            isPositive = isPositive,
            pageable = pageable,
        )
        return ApiResult(data = result)
    }
}
