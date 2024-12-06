package com.chatservice.common.response

data class ApiResult<T>(
    val code: String = "",
    val message: String = "",
    val data: T? = null,
)
