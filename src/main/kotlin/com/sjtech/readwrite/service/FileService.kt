package com.sjtech.readwrite.service

import com.sjtech.readwrite.common.constants.ServiceApplicationObjects.FILE_STORAGE_MAP
import com.sjtech.readwrite.common.utils.ServiceUtils
import org.slf4j.Logger
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class FileService(val log: Logger,
                  private val jobLauncher: JobLauncher,
                  private val job: Job) {

    @Async
    fun initiateBatchJob(fileName: String, inMemFile: ByteArray) {
        val jobParameters = JobParametersBuilder()
                .addString("fileName", fileName)
                .addLong("jobID", ServiceUtils.generateJobID())
                .toJobParameters()

        FILE_STORAGE_MAP.put(fileName, inMemFile)
        jobLauncher.run(job, jobParameters)
    }
}