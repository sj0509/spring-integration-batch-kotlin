package com.sjtech.readwrite.batch

import com.sjtech.readwrite.constants.ReadWriteConstants.TRADE_MAP
import com.sjtech.readwrite.model.Trade
import org.slf4j.Logger
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.stereotype.Component

@Component
class JobCompletionNotificationListener(private val log: Logger) : JobExecutionListenerSupport() {

    override fun afterJob(jobExecution: JobExecution) {

        if (jobExecution.status === BatchStatus.COMPLETED) {
            log.info("============= JOB FINISHED =============")
            log.info("Start time: ${jobExecution.startTime}")
            log.info("End time: ${jobExecution.endTime}")

            val jobId = jobExecution.jobParameters.getString("jobID")
            val tradesListCopy: List<Trade>? = TRADE_MAP.get(jobId)?.map { it.copy() }

            log.trace("Map size before: ${TRADE_MAP.size}")
            TRADE_MAP.remove(jobId)
            log.trace("Map size after: ${TRADE_MAP.size}")

            tradesListCopy?.forEach {
                log.debug("$it")
            }
        }
    }

}
