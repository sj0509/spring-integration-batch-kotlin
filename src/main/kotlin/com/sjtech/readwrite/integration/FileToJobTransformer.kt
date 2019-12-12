package com.sjtech.readwrite.integration

import com.sjtech.readwrite.AppUtils
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.integration.launch.JobLaunchRequest
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.integration.annotation.Transformer
import org.springframework.stereotype.Component
import java.io.File


@Component
class FileToJobTransformer {

    @Autowired
    private val job: Job? = null

    private var context: ApplicationContext? = null

    @Transformer(inputChannel = "fileToJobProcessor", outputChannel = "jobChannel")
    fun transform(aFile: File): JobLaunchRequest? {
        val fileName = aFile.absolutePath
        val jobParameters = JobParametersBuilder()
                .addString("fileName", fileName)
                .addLong("jobID", AppUtils.generateJobID())
                .toJobParameters()
        return JobLaunchRequest(job!!, jobParameters)
    }

    @Throws(BeansException::class)
    fun setApplicationContext(applicationContext: ApplicationContext?) {
        context = applicationContext
    }
}