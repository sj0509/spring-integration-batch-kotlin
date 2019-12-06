package com.sjtech.readwrite.batch

import com.sjtech.readwrite.model.Trade
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class TradeProcessor : ItemProcessor<Trade, Trade> {

    override fun process(item: Trade): Trade? {
        return item
    }
}