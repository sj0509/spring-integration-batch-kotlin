package com.sjtech.readwrite.common.constants

import com.sjtech.readwrite.db.entity.Trade

object ServiceApplicationObjects {

    val FILE_STORAGE_MAP: MutableMap<String, ByteArray> = mutableMapOf()
    val TRADE_MAP: MutableMap<Long, MutableList<Trade>> = mutableMapOf()

}