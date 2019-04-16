package br.com.rik.rikchallenge.model

import java.time.LocalDateTime
import java.util.*

data class PaymentOrderStatus(
    val id: String,
    val orderId: String,
    val orderStatus: OrderStatus,
    val payloadType: PayloadType,
    val payload: String,
    val statusDate: LocalDateTime
)

enum class OrderStatus {
    VALID,
    SUCCESS,
    INVALID_CREDIT_CARD,
    CREDIT_CARD_PAYMENT_FAIL,
    GENERAL_FAIL
}

enum class PayloadType {
    BOLETO_VALIDATE,
    BOLETO_NUMBER,
    CREDIT_CARD_VALIDATE,
    CREDIT_CARD_PROCESS
}

