package com.chatservice.common.exception


import com.chatservice.common.logger.logger
import com.chatservice.common.response.ApiResult
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    fun exceptionHandler(e: IllegalArgumentException): ApiResult<String> {
        logger().error(e.message, e)
        return ApiResult(message = e.message ?: "Unknown error")
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    fun exceptionHandler(e: IllegalStateException): ApiResult<String> {
        logger().error(e.message, e)
        return ApiResult(message = e.message ?: "Unknown error")
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    fun exceptionHandler(e: NotFoundException): ApiResult<String> {
        logger().error(e.message, e)
        return ApiResult(message = e.message ?: "Unknown error")
    }
}
