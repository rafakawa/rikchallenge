package br.com.rik.rikchallenge.factory

import br.com.rik.rikchallenge.model.Buyer
import br.com.rik.rikchallenge.model.Client
import br.com.rik.rikchallenge.model.Payment
import br.com.rik.rikchallenge.model.PaymentOrder
import java.time.LocalDateTime
import java.util.*

class PaymentOrderFactory {

    fun buildPaymentOrder(client: Client, buyer: Buyer, payment: Payment) : PaymentOrder {
        val id = UUID.randomUUID().toString()
        val date = LocalDateTime.now()

        return PaymentOrder(id, client, buyer, payment, date)
    }

}