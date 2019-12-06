package com.sjtech.readwrite

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.integration.config.EnableIntegration
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@EnableIntegration
@EnableBatchProcessing
class SpringIntegrationBatchKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringIntegrationBatchKotlinApplication>(*args)
}
