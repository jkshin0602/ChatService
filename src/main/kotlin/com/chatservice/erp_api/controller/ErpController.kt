package com.chatservice.erp_api.controller

import com.chatservice.common.response.ApiResult
import com.chatservice.erp_api.dto.response.ActivityRecordApiResult
import com.chatservice.erp_api.service.UserActivityFinder
import com.chatservice.user.domain.AuthUser
import com.chatservice.user.domain.AuthenticatedUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/admin/erp")
@RestController
class ErpController(
    private val userActivityFinder: UserActivityFinder,
) {
    @GetMapping("/user-activity")
    fun getUserActivityRecords(
        @AuthUser user: AuthenticatedUser,
    ): ApiResult<ActivityRecordApiResult> {
        val result = userActivityFinder.getActivityInfo(user.id)
        return ApiResult(data = result)
    }
}
