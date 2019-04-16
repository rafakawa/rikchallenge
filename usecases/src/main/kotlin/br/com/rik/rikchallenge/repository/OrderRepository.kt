package br.com.rik.rikchallenge.repository

import br.com.rik.rikchallenge.model.PaymentOrder
import br.com.rik.rikchallenge.model.PaymentOrderStatus

interface OrderRepository {

    fun saveOrder(paymentOrder: PaymentOrder)

    fun getOrderById(id: String) : PaymentOrder

    fun saveOrderStatus(paymentOrderStatus: PaymentOrderStatus)

    fun getOrderStatusById(id: String) : PaymentOrderStatus
}