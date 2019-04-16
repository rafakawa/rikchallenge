package br.com.rik.rikchallenge.order.paymentStrategy

import br.com.rik.rikchallenge.model.PaymentOrder
import br.com.rik.rikchallenge.model.PaymentType

// I am just assuming that default is Boleto
fun PaymentOrder.buildPaymentStrategy() : PaymentStrategy = when {
    this.payment.type == PaymentType.BOLETO -> BoletoPayment()
    this.payment.type == PaymentType.CREDIT_CARD -> CreditCardPayment()
    else -> BoletoPayment()
}