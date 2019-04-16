package br.com.rik.rikchallenge.order

import br.com.rik.rikchallenge.factory.PaymentOrderFactory
import br.com.rik.rikchallenge.model.Card as CardModel
import br.com.rik.rikchallenge.model.Payment as PaymentModel
import br.com.rik.rikchallenge.model.Buyer as BuyerModel
import br.com.rik.rikchallenge.model.Client as ClientModel
import br.com.rik.rikchallenge.model.PaymentOrder
import br.com.rik.rikchallenge.model.PaymentType

fun ProcessOrderRequestInput.MapToPaymentOrder() : PaymentOrder {

    val client = ClientModel(this.client.id)

    val buyer = BuyerModel(this.buyer.name, this.buyer.eMail, this.buyer.cpf)

    val card = if(this.payment.type == "CREDIT_CARD" && this.payment.card != null) {
        CardModel(this.payment.card.holderName, this.payment.card.number, this.payment.card.expirationDate, this.payment.card.cvv)
    } else {
        null
    }

    val payment = PaymentModel(this.payment.amount, PaymentType.valueOf(this.payment.type), card)

    return PaymentOrderFactory().buildPaymentOrder(client, buyer, payment)
}