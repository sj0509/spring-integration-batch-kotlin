package com.sjtech.readwrite.batch

import com.sjtech.readwrite.common.constants.ServiceApplicationObjects.TRADE_MAP
import com.sjtech.readwrite.db.entity.Trade
import org.slf4j.Logger
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@StepScope
class TradesListWriter<T>(private val log: Logger) : ItemWriter<Trade> {

    @Value("#{jobParameters['jobID']}")
    var jobId: Long = 0

    override fun write(items: MutableList<out Trade>) {

        TRADE_MAP.getOrPut(jobId) { mutableListOf() }.addAll(items)
        log.debug("######### Inside write - items.size: ${items.size}, tradesList.size: ${TRADE_MAP.get(jobId)?.size}")

    }

}