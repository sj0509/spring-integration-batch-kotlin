package com.sjtech.readwrite.integration

import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.core.MessageSource
import org.springframework.integration.file.FileReadingMessageSource
import org.springframework.integration.file.filters.SimplePatternFileListFilter
import org.springframework.messaging.MessageChannel
import org.springframework.stereotype.Component
import java.io.File


@Component
class IntegrationConfig {

    @Value("\${coindump.dir.incoming}")
    private val incomingDir: String? = null

    @Bean
    fun fileInputChannel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    fun pgpFileProcessor(): DirectChannel {
        return DirectChannel()
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = [Poller(fixedDelay = "5000", maxMessagesPerPoll = "2")])
    fun fileReadingMessageSource(): MessageSource<File?>? {
        val source = FileReadingMessageSource()
        source.setDirectory(File(incomingDir))
        source.setFilter(SimplePatternFileListFilter("*.csv"))
        source.setScanEachPoll(true)
        source.setUseWatchService(true)
        return source
    }

    @Bean
    @ServiceActivator(inputChannel = "jobChannel", outputChannel = "nullChannel")
    protected fun launcher(jobLauncher: JobLauncher): JobLaunchingMessageHandler {
        return JobLaunchingMessageHandler(jobLauncher)
    }
}