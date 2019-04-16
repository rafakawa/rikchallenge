package br.com.rik.rikchallenge.order

import br.com.rik.rikchallenge.*
import br.com.rik.rikchallenge.model.*
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class MySqlOrderRepositoryTest : Setup() {

    @Test
    fun happyTestOrder() {

        // Arrange
        val repository = MySqlOrderRepository(jdbi)

        val id = "orderId1"
        val paymentOrder = PaymentOrder(
            id,
            Client("id1"),
            Buyer("name1", "email@mail.com", "1234567"),
            Payment(150.0, PaymentType.BOLETO, null),
            LocalDateTime.now()
        )

        // Act
        repository.saveOrder(paymentOrder)

        val result = repository.getOrderById(id)

        assertEquals(id, result.id)
    }

    @Test
    fun happyTestOrderStatus() {

        // Arrange
        val repository = MySqlOrderRepository(jdbi)

        val paymentOrder = PaymentOrder(
            "order1",
            Client("id1"),
            Buyer("name1", "email@mail.com", "1234567"),
            Payment(150.0, PaymentType.BOLETO, null),
            LocalDateTime.now()
        )
        repository.saveOrder(paymentOrder)

        val id = "orderStatusId1"
        val paymentOrderStatus =
            PaymentOrderStatus(id, "order1", OrderStatus.SUCCESS, PayloadType.BOLETO_NUMBER, "333222", LocalDateTime.now())

        // Act
        repository.saveOrderStatus(paymentOrderStatus)

        val result = repository.getOrderStatusById(id)

        assertEquals(id, result.id)
        assertEquals("333222", result.payload)

    }

}