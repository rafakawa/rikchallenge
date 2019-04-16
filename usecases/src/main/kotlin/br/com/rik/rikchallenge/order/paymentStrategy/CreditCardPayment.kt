package br.com.rik.rikchallenge.order.paymentStrategy

import br.com.rik.rikchallenge.model.OrderStatus
import br.com.rik.rikchallenge.model.PayloadType
import br.com.rik.rikchallenge.model.PaymentOrder
import br.com.rik.rikchallenge.order.ProcessOrderOutput
import br.com.rik.rikchallenge.model.validate

class CreditCardPayment : PaymentStrategy {

    override fun validatePayment(paymentOrder: PaymentOrder): ProcessOrderOutput {
        if (paymentOrder.payment.card!!.validate())
            return ProcessOrderOutput(paymentOrder.id, OrderStatus.VALID, PayloadType.CREDIT_CARD_VALIDATE,"")
        return ProcessOrderOutput(paymentOrder.id, OrderStatus.INVALID_CREDIT_CARD, PayloadType.CREDIT_CARD_VALIDATE,"")
    }

    override fun runPayment(paymentOrder: PaymentOrder): ProcessOrderOutput {
        val paymentStatus = generatingPaymentStatus(paymentOrder)

        return ProcessOrderOutput(paymentOrder.id, paymentStatus, PayloadType.CREDIT_CARD_PROCESS,"")
    }

    // Here I am just mocking the response, as we are not actually validating the Credit Card payment
    fun generatingPaymentStatus(paymentOrder: PaymentOrder): OrderStatus = when {
        paymentOrder.client.id == "failTest" -> OrderStatus.CREDIT_CARD_PAYMENT_FAIL
        else -> OrderStatus.SUCCESS
    }

}