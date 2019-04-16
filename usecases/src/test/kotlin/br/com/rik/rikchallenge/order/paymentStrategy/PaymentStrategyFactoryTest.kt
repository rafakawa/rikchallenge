package br.com.rik.rikchallenge.order.paymentStrategy

import br.com.rik.rikchallenge.model.*
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

class PaymentStrategyFactoryTest {

    @Test
    fun happyTestBuildBoleto() {

        // Arrange
        val paymentOrder = PaymentOrder(
            "orderId1",
            Client("id1"),
            Buyer("name1", "email@mail.com", "1234567"),
            Payment(150.0, PaymentType.BOLETO, null),
            LocalDateTime.now()
        )

        // Act
        val result = paymentOrder.buildPaymentStrategy()

        // Assert
        assertTrue(result is BoletoPayment)
    }

    @Test
    fun happyTestBuildCreditCard() {

        // Arrange
        val paymentOrder = PaymentOrder(
            "orderId1",
            Client("id1"),
            Buyer("name1", "email@mail.com", "1234567"),
            Payment(
                150.0, PaymentType.CREDIT_CARD,
                Card("MyName", "5555666677778884", "09/2019", "321")
            ),
            LocalDateTime.now()
        )

        // Act
        val result = paymentOrder.buildPaymentStrategy()

        // Assert
        assertTrue(result is CreditCardPayment)
    }
}