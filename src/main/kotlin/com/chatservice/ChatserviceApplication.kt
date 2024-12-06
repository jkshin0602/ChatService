package com.chatservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatserviceApplication

fun main(args: Array<String>) {
	runApplication<ChatserviceApplication>(*args)
}
