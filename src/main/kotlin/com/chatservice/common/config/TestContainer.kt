package com.chatservice.common.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import javax.sql.DataSource

class TestContainer {
    init {
        startContainer()
    }

    private fun startContainer(maxAttempts: Int = 3, delayMs: Long = 1000) {
        var attempt = 1

        while (attempt <= maxAttempts) {
            runCatching {
                if (!container.isRunning) container.start()
                return
            }.onFailure {
                Thread.sleep(delayMs)
                attempt++
            }
        }
        throw RuntimeException("Test Container Start Failed")
    }

    companion object {
        val container = PostgreSQLContainer(
            DockerImageName.parse("postgres:15.8")
        ).apply {
            withDatabaseName("postgres")
            withUsername("postgres")
            withPassword("postgres")
        }
    }
}

@Profile("!test")
@Configuration
class DatabaseConfig {
    init {
        TestContainer()
    }

    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
            .url(TestContainer.container.jdbcUrl)
            .username(TestContainer.container.username)
            .password(TestContainer.container.password)
            .driverClassName("org.postgresql.Driver")
            .build()
    }
}
