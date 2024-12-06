package com.chatservice.user_api.controller

import com.chatservice.common.response.ApiResult
import com.chatservice.user_api.dto.request.JoinUserApiRequest
import com.chatservice.user_api.dto.request.LoginUserApiRequest
import com.chatservice.user_api.dto.response.JoinUserApiResponse
import com.chatservice.user_api.dto.response.LoginUserApiResponse
import com.chatservice.user_api.service.JoinUserService
import com.chatservice.user_api.service.LoginUserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/users")
@RestController
class UserController(
    private val joinUserService: JoinUserService,
    private val loginUserService: LoginUserService,
) {
    @PostMapping
    fun join(
        @RequestBody request: JoinUserApiRequest,
    ): ApiResult<JoinUserApiResponse> {
        val result = joinUserService.join(request)
        return ApiResult(data = result)
    }

    @PostMapping
    fun login(
        @RequestBody request: LoginUserApiRequest,
    ): ApiResult<LoginUserApiResponse> {
        val result = loginUserService.login(request)
        return ApiResult(data = result)
    }
}
