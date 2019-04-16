package br.com.rik.rikchallenge.factory

import br.com.rik.rikchallenge.model.Buyer
import br.com.rik.rikchallenge.model.Client
import br.com.rik.rikchallenge.model.Payment
import br.com.rik.rikchallenge.model.PaymentType
import org.junit.Assert.assertTrue
import org.junit.Test

class PaymentOrderFactoryTest {

    @Test
    fun WhenBuildingPaymentOrder_ShouldCreateIdAndSetDate() {
        // Arrange
        val client = Client("clientId")
        val buyer = Buyer("buyer", "buyer@email.com", "32033333")
        val payment = Payment(320.4, PaymentType.BOLETO, null)

        //Act
        val result = PaymentOrderFactory().buildPaymentOrder(client, buyer, payment)

        // Assert
        assertTrue(result.id.isNotEmpty())
        assertTrue(result.created != null)

    }
}