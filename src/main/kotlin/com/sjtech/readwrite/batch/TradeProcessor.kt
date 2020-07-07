package com.sjtech.readwrite.batch

import com.sjtech.readwrite.db.entity.Trade
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@StepScope
class TradeProcessor : ItemProcessor<Trade, Trade> {

    @Value("#{jobParameters['jobID']}")
    var jobID: Long = 0

    override fun process(trade: Trade): Trade {
        return trade.copy(
                jobID = jobID
        )
    }
}