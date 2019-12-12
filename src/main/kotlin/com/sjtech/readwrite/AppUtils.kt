package com.sjtech.readwrite

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object AppUtils {

    fun generateJobID() = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssM")).toLong()
}