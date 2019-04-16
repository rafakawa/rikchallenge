package br.com.rik.rikchallenge.order.paymentStrategy

import br.com.rik.rikchallenge.model.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

class BoletoPaymentTest {

    // When running a payment
    // 1 - It should return SUCCESS
    // 2 - Generate a number at Payload
    @Test
    fun happyTestRunPayment() {
        // Arrange
        val boletoPayment = BoletoPayment()
        val paymentOrder = PaymentOrder(
            "orderId",
            Client("id1"),
            Buyer("name1", "email@mail.com", "1234567"),
            Payment(150.0, PaymentType.BOLETO, null),
            LocalDateTime.now()
        )

        // Act
        val result = boletoPayment.runPayment(paymentOrder)

        // Assert
        assertEquals(OrderStatus.SUCCESS , result.status)
        assertTrue(result.payload.isNotEmpty())
    }

    // When generating a Boleto number
    // 1 - It should have 20 in length
    // 2 - All chars should be a digit
    @Test
    fun happyTestGenerateBoletoNumber() {
        // Arrange
        val boletoPayment = BoletoPayment()

        // Act
        val result = boletoPayment.generateBoletoNumber()

        // Assert
        assertEquals(20, result.length)
        assertTrue(result.all { it.isDigit() })
    }

}