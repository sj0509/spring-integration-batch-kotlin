package com.sjtech.readwrite.batch

import org.slf4j.Logger
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.listener.StepExecutionListenerSupport
import org.springframework.stereotype.Component

@Component
class StepNotificationListener(private val log: Logger) : StepExecutionListenerSupport() {

    override fun beforeStep(stepExecution: StepExecution) {
        log.info("Before executing step: ${stepExecution.stepName}")
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus {
        log.info("After executing step: ${stepExecution.stepName}, with status: ${stepExecution.exitStatus}")
        return stepExecution.exitStatus
    }
}