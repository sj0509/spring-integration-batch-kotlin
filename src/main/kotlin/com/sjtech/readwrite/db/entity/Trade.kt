package com.sjtech.readwrite.db.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "TRADE")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Trade(
        @JsonIgnore
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,
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

    constructor() : this(0, null, null, null, null, null, null, null, null, 0)
}