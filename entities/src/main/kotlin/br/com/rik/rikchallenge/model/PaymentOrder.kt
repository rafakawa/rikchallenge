package br.com.rik.rikchallenge.model

import java.time.LocalDateTime
import java.util.*

data class PaymentOrder (
    val id: String,
    val client: Client,
    val buyer: Buyer,
    val payment: Payment,
    val created: LocalDateTime
)

