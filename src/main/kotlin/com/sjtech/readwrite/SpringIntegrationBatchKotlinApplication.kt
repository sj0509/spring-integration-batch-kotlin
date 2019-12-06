package com.sjtech.readwrite

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAsy
class SpringIntegrationBatchKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringIntegrationBatchKotlinApplication>(*args)
}
