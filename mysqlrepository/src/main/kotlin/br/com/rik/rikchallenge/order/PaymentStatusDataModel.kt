package br.com.rik.rikchallenge.order

import java.time.LocalDateTime

data class PaymentStatusDataModel (
    val id: String,
    val orderId: String,
    val orderStatus: String,
    val payloadType: String,
    val payload: String,
    val statusDate: LocalDateTime
)