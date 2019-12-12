package com.sjtech.readwrite.model

data class Trade(
        var date: String? = null,
        var market: String? = null,
        var transactionType: String? = null,
        var price: Double? = null,
        var amount: Double? = null,
        var total: Double? = null,
        var fee: Double? = null,
        var feeCoin: String? = null,
        val jobID: Long = 0
) {

    constructor() : this(null, null, null, null, null, null, null, null, 0)
}