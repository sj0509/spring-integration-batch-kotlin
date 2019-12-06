package com.sjtech.readwrite.batch

import com.sjtech.readwrite.model.Trade
import org.slf4j.Logger
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.support.CompositeItemWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class BatchJobConfig(private val jobBuilderFactory: JobBuilderFactory,
                     private val jobCompletionNotificationListener: JobCompletionNotificationListener,
                     private val tradesListWriter: TradesListWriter<Trade>,
                     private val log: Logger,
                     private val stepBuilderFactory: StepBuilderFactory) {

    @Bean
    fun readCsvFilesJob(): Job {
        return jobBuilderFactory
                .get("readCSVFilesJob")
                .incrementer(RunIdIncrementer())
                .listener(jobCompletionNotificationListener)
                .flow(step1())
                .end()
                .build()
    }

    @Bean
    fun step1(): Step {

        return stepBuilderFactory["step1"]
                .chunk<Trade, Trade>(100)
                .reader(reader(""))
                .processor(TradeProcessor())
                .writer(compositeItemWriter())
                .taskExecutor(threadPoolTaskExecutor())
                .build()
    }

    @Bean
    fun threadPoolTaskExecutor(): TaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 1
        executor.maxPoolSize = 1
        executor.setThreadNamePrefix("default_task_executor_thread")
        executor.initialize()
        return executor
    }

    @Bean
    @StepScope
    fun reader(@Value("#{jobParameters[fileName]}") fileName: String?): FlatFileItemReader<Trade> {
        log.debug("FileName: $fileName")
        val reader: FlatFileItemReader<Trade> = FlatFileItemReader()
        reader.setResource(FileSystemResource(fileName!!))
        reader.setLinesToSkip(1)
        reader.setLineMapper(object : DefaultLineMapper<Trade>() {
            init {
                setLineTokenizer(lineTokenizer())
                setFieldSetMapper(object : BeanWrapperFieldSetMapper<Trade>() {
                    init {
                        setTargetType(Trade::class.java)
                    }
                })
            }
        }
        )

        return reader
    }

    @Bean
    fun lineTokenizer(): DelimitedLineTokenizer {
        val tokenizer = DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_COMMA)
        tokenizer.setNames("date", "market", "transactionType", "price", "amount", "total", "fee", "feeCoin")
        return tokenizer
    }

    @Bean
    fun compositeItemWriter(): ItemWriter<in Trade> {
        val compositeItemWriter: CompositeItemWriter<Trade> = CompositeItemWriter()
        compositeItemWriter.setDelegates(listOf(tradesListWriter))
        return compositeItemWriter
    }
}