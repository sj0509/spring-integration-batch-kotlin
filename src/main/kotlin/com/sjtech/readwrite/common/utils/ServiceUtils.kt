package com.sjtech.readwrite.common.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ServiceUtils {

    fun generateJobID(): Long {
        val formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssM"))
        return formatDateTime.toLong()
    }
}