package br.com.rik.rikchallenge.order

import br.com.rik.rikchallenge.model.*
import br.com.rik.rikchallenge.repository.OrderRepository
import br.com.rik.rikchallenge.repository.exceptions.PaymentOrderNotFoundException
import br.com.rik.rikchallenge.repository.exceptions.PaymentStatusNotFoundException
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Repository

@Repository
class MySqlOrderRepository(private val db: Jdbi) : OrderRepository {

    override fun saveOrder(paymentOrder: PaymentOrder) {
        val orderDao = db.onDemand(OrderDAO::class.java)

        orderDao.saveOrder(
            paymentOrder.id,
            paymentOrder.client.id,
            paymentOrder.buyer.name,
            paymentOrder.buyer.eMail,
            paymentOrder.buyer.cpf,
            paymentOrder.payment.amount,
            paymentOrder.payment.type.toString(),
            paymentOrder.payment.card?.holderName,
            paymentOrder.payment.card?.number,
            paymentOrder.payment.card?.expirationDate,
            paymentOrder.payment.card?.cvv,
            paymentOrder.created
        )
    }

    override fun getOrderById(id: String) : PaymentOrder {
        val orderDao = db.onDemand(OrderDAO::class.java)

        val paymentDataModel = orderDao.findOrderById(id) ?: throw PaymentOrderNotFoundException("Payment Order : $id not found")

        val card = if(paymentDataModel.paymentType == "CREDIT_CARD")
            Card(
                paymentDataModel.cardHolderName!!,
                paymentDataModel.cardNumber!!,
                paymentDataModel.cardExpirationDate!!,
                paymentDataModel.cardCvv!!
            )
                   else null

        val paymentOrder = PaymentOrder(
            paymentDataModel.id,
            Client(paymentDataModel.clientId),
            Buyer(
                paymentDataModel.buyerName,
                paymentDataModel.buyerEmail,
                paymentDataModel.buyerCpf
            ),
            Payment(
                paymentDataModel.paymentAmount,
                PaymentType.valueOf(paymentDataModel.paymentType),
                card
            ),
            paymentDataModel.orderDate
        )

        return paymentOrder
    }

    override fun saveOrderStatus(paymentOrderStatus: PaymentOrderStatus) {
        val orderDao = db.onDemand(OrderDAO::class.java)

        orderDao.saveOrderStatus(
            paymentOrderStatus.id,
            paymentOrderStatus.orderId,
            paymentOrderStatus.orderStatus.toString(),
            paymentOrderStatus.payloadType.toString(),
            paymentOrderStatus.payload,
            paymentOrderStatus.statusDate
        )
    }

    override fun getOrderStatusById(id: String): PaymentOrderStatus {
        val orderDao = db.onDemand(OrderDAO::class.java)

        val result = orderDao.findOrderStatusById(id) ?: throw PaymentStatusNotFoundException("Order status $id nor found.")

        return PaymentOrderStatus(
            result.id,
            result.orderId,
            OrderStatus.valueOf(result.orderStatus),
            PayloadType.valueOf(result.payloadType),
            result.payload,
            result.statusDate
        )
    }

}