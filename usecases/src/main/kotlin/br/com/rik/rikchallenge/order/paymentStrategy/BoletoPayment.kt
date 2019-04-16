package br.com.rik.rikchallenge.order.paymentStrategy

import br.com.rik.rikchallenge.model.OrderStatus
import br.com.rik.rikchallenge.model.PayloadType
import br.com.rik.rikchallenge.model.PaymentOrder
import br.com.rik.rikchallenge.order.ProcessOrderOutput

class BoletoPayment : PaymentStrategy {

    override fun validatePayment(paymentOrder: PaymentOrder): ProcessOrderOutput {
        return ProcessOrderOutput(paymentOrder.id, OrderStatus.VALID, PayloadType.BOLETO_VALIDATE, "")
    }

    override fun runPayment(paymentOrder: PaymentOrder) : ProcessOrderOutput {

        val boletoNumber = generateBoletoNumber()

        return ProcessOrderOutput(paymentOrder.id, OrderStatus.SUCCESS, PayloadType.BOLETO_NUMBER, boletoNumber)

    }

    // Generating some random Boleto Number
    fun generateBoletoNumber() : String {
        return (1..20)
            .map { _ -> kotlin.random.Random.nextInt(0, 9) }
            .joinToString("");
    }
}