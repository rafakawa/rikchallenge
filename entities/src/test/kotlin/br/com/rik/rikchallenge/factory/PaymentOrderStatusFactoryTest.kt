package br.com.rik.rikchallenge.factory

import br.com.rik.rikchallenge.model.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PaymentOrderStatusFactoryTest {

    @Test
    fun WhenBuildingPaymentOrderStatus_ShouldCreateIdAndSetDate() {
        // Arrange
        val orderId = "order1"

        //Act
        val result = PaymentOrderStatusFactory().buildPaymentOrderStatus(orderId, OrderStatus.SUCCESS, PayloadType.BOLETO_NUMBER, "321")

        // Assert
        assertTrue(result.id.isNotEmpty())
        assertTrue(result.statusDate != null)
        assertEquals(orderId, result.orderId)
    }
}