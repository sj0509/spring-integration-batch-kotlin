package com.sjtech.readwrite.batch

import com.sjtech.readwrite.constants.ReadWriteConstants.TRADE_MAP
import com.sjtech.readwrite.model.Trade
import org.slf4j.Logger
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@StepScope
class TradesListWriter<T>(private val log: Logger) : ItemWriter<Trade> {

    @Value("#{jobParameters['jobID']}")
    lateinit var jobId: String

    override fun write(items: MutableList<out Trade>) {

        TRADE_MAP.getOrPut(jobId) { mutableListOf() }.addAll(items)
        log.debug("######### Inside write - items.size: ${items.size}, tradesList.size: ${TRADE_MAP.get(jobId)?.size}")

    }

}