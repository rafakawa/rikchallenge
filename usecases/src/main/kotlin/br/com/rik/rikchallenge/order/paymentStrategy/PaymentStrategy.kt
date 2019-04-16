package br.com.rik.rikchallenge.order.paymentStrategy

import br.com.rik.rikchallenge.model.PaymentOrder
import br.com.rik.rikchallenge.order.ProcessOrderOutput

interface PaymentStrategy {

    fun validatePayment(paymentOrder: PaymentOrder) : ProcessOrderOutput

    fun runPayment(paymentOrder: PaymentOrder) : ProcessOrderOutput

}

