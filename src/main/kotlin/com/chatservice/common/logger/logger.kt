package com.chatservice.common.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun logger(): Logger = LoggerFactory.getLogger(Logger::class.java)
