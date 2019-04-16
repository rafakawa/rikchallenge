package br.com.rik.rikchallenge.model

data class Payment(
    val amount: Double,
    val type: PaymentType,
    val card: Card?
)

