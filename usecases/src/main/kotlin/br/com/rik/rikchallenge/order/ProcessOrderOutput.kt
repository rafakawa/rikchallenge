package br.com.rik.rikchallenge.order

import br.com.rik.rikchallenge.factory.PaymentOrderStatusFactory
import br.com.rik.rikchallenge.model.OrderStatus
import br.com.rik.rikchallenge.model.PayloadType
import br.com.rik.rikchallenge.model.PaymentOrderStatus

data class ProcessOrderOutput(
    val processOrderId: String,
    val status: OrderStatus,
    val payloadType: PayloadType,
    val payload: String
)

fun ProcessOrderOutput.toPaymentOrderStatus(orderId: String) : PaymentOrderStatus {

    return PaymentOrderStatusFactory().buildPaymentOrderStatus(
        orderId,
        this.status,
        this.payloadType,
        this.payload
    )

}

