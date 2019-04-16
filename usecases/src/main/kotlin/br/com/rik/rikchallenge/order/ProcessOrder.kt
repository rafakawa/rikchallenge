package br.com.rik.rikchallenge.order

import br.com.rik.rikchallenge.factory.PaymentOrderStatusFactory
import br.com.rik.rikchallenge.model.OrderStatus
import br.com.rik.rikchallenge.model.PaymentOrder
import br.com.rik.rikchallenge.model.PaymentOrderStatus
import br.com.rik.rikchallenge.order.paymentStrategy.buildPaymentStrategy
import br.com.rik.rikchallenge.repository.OrderRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class ProcessOrder(
    val orderRepository: OrderRepository
) {

    // Process is finding its payment type
    // Validating data
    // Running payment if valid
    fun process(paymentOrder: PaymentOrder) : ProcessOrderOutput {

        orderRepository.saveOrder(paymentOrder)

        val paymentType = paymentOrder.buildPaymentStrategy()

        val paymentValidReturn = paymentType.validatePayment(paymentOrder)

        orderRepository.saveOrderStatus(paymentValidReturn.toPaymentOrderStatus(paymentOrder.id))

        if(paymentValidReturn.status != OrderStatus.VALID) {
            return paymentValidReturn
        }

        val result = paymentType.runPayment(paymentOrder)

        orderRepository.saveOrderStatus(result.toPaymentOrderStatus(paymentOrder.id))

        return result
    }

}