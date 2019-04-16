package br.com.rik.rikchallenge.order

import java.time.LocalDateTime

data class PaymentOrderDataModel (
    val id: String,
    val clientId: String,
    val buyerName: String,
    val buyerEmail: String,
    val buyerCpf: String,
    val paymentAmount: Double,
    val paymentType: String,
    val cardHolderName: String?,
    val cardNumber: String?,
    val cardExpirationDate: String?,
    val cardCvv: String?,
    val orderDate: LocalDateTime
)