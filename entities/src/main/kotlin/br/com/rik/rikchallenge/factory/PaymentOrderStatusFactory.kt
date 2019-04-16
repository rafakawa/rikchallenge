package br.com.rik.rikchallenge.factory

import br.com.rik.rikchallenge.model.OrderStatus
import br.com.rik.rikchallenge.model.PayloadType
import br.com.rik.rikchallenge.model.PaymentOrderStatus
import java.time.LocalDateTime
import java.util.*

class PaymentOrderStatusFactory {

    fun buildPaymentOrderStatus(orderId: String, orderStatus: OrderStatus, payloadType: PayloadType, payload: String) : PaymentOrderStatus {
        val id = UUID.randomUUID().toString()
        val date = LocalDateTime.now()

        return PaymentOrderStatus(id, orderId, orderStatus, payloadType, payload, date)
    }

}