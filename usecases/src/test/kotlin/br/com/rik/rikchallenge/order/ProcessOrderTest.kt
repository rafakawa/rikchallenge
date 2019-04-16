package br.com.rik.rikchallenge.order

import br.com.rik.rikchallenge.model.*
import br.com.rik.rikchallenge.repository.OrderRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

class ProcessOrderTest {

    @Test
    fun testProcessOrdeForBoleto() {
        // Arrange
        val orderRepository = mock<OrderRepository>();

        val processOrder = ProcessOrder(orderRepository)

        val paymentOrder = PaymentOrder(
            "order1",
            Client("id1"),
            Buyer("name1", "email@mail.com", "1234567"),
            Payment(150.0, PaymentType.BOLETO, null),
            LocalDateTime.now()
        )

        // Act
        val result = processOrder.process(paymentOrder)

        // Assert

        // Verify that we saved order and status
        verify(orderRepository, times(1)).saveOrder(paymentOrder)
        verify(orderRepository, times(2)).saveOrderStatus(any())

        // Verify result, we should have generated a Boleto
        assertEquals(OrderStatus.SUCCESS, result.status)
        assertEquals(PayloadType.BOLETO_NUMBER, result.payloadType)
        assertTrue(result.payload.isNotEmpty())
    }

    @Test
    fun testProcessOrdeForValidCreditCard() {
        // Arrange
        val orderRepository = mock<OrderRepository>();

        val processOrder = ProcessOrder(orderRepository)

        val card = Card("name1", "5555666677778884", "09/2020", "321")

        val paymentOrder = PaymentOrder(
            "order1",
            Client("id1"),
            Buyer("name1", "email@mail.com", "1234567"),
            Payment(150.0, PaymentType.CREDIT_CARD, card),
            LocalDateTime.now()
        )

        // Act
        val result = processOrder.process(paymentOrder)

        // Assert

        // Verify that we saved order and status
        verify(orderRepository, times(1)).saveOrder(paymentOrder)
        verify(orderRepository, times(2)).saveOrderStatus(any())

        // Verify result, we should have processsed credit card
        assertEquals(OrderStatus.SUCCESS, result.status)
        assertEquals(PayloadType.CREDIT_CARD_PROCESS, result.payloadType)
        assertTrue(result.payload.isEmpty())
    }

    @Test
    fun testProcessOrdeForInvalidCreditCard() {
        // Arrange
        val orderRepository = mock<OrderRepository>();

        val processOrder = ProcessOrder(orderRepository)

        val card = Card("name1", "312312", "09/2020", "321")

        val paymentOrder = PaymentOrder(
            "order1",
            Client("id1"),
            Buyer("name1", "email@mail.com", "1234567"),
            Payment(150.0, PaymentType.CREDIT_CARD, card),
            LocalDateTime.now()
        )

        // Act
        val result = processOrder.process(paymentOrder)

        // Assert

        // Verify that we saved order and status
        verify(orderRepository, times(1)).saveOrder(paymentOrder)
        verify(orderRepository, times(1)).saveOrderStatus(any())

        // Verify result, we should have processsed credit card
        assertEquals(OrderStatus.INVALID_CREDIT_CARD, result.status)
        assertEquals(PayloadType.CREDIT_CARD_VALIDATE, result.payloadType)
        assertTrue(result.payload.isEmpty())
    }
}