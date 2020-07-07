package com.sjtech.readwrite.batch

import com.sjtech.readwrite.common.constants.ServiceApplicationObjects.FILE_STORAGE_MAP
import com.sjtech.readwrite.common.constants.ServiceApplicationObjects.TRADE_MAP
import com.sjtech.readwrite.db.entity.Trade
import org.slf4j.Logger
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.stereotype.Component

@Component
class JobCompletionNotificationListener(private val log: Logger) : JobExecutionListenerSupport() {

    override fun afterJob(jobExecution: JobExecution) {

        val jobId = jobExecution.jobParameters.getLong("jobID")
        val fileName = jobExecution.jobParameters.getString("fileName")

        if (jobExecution.status === BatchStatus.COMPLETED) {
            log.info("============= JOB FINISHED =============")
            log.info("Start time: ${jobExecution.startTime}")
            log.info("End time: ${jobExecution.endTime}")

            val tradesListCopy: List<Trade>? = TRADE_MAP.get(jobId)?.map { it.copy() }

            cleanUpCache(jobId, fileName)

            tradesListCopy?.forEach {
                log.trace("$it")
            }

            jobExecution.stepExecutions.forEach {
                log.info(it.summary)
            }
        } else {
            cleanUpCache(jobId, fileName)
            log.info(jobExecution.stepExecutions.iterator().next().summary)
        }
    }

    private fun cleanUpCache(jobId: Long?, fileName: String?) {
        log.debug("Before TRADE_MAP.size: ${TRADE_MAP.size}, FILE_STORAGE_MAP.size: ${FILE_STORAGE_MAP.size}")
        TRADE_MAP.remove(jobId)
        FILE_STORAGE_MAP.remove(fileName)
        log.debug("After TRADE_MAP.size: ${TRADE_MAP.size}, FILE_STORAGE_MAP.size: ${FILE_STORAGE_MAP.size}")
    }

}
