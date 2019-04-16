package br.com.rik.rikchallenge.order

data class ProcessOrderRequestInput (
    val client: Client,
    val buyer: Buyer,
    val payment: Payment
)

data class Client(
    val id: String
)

data class Buyer(
    val name: String,
    val eMail: String,
    val cpf: String
)

data class Payment(
    val amount: Double,
    val type: String,
    val card: Card?
)

data class Card(
    val holderName: String,
    val number: String,
    val expirationDate: String,
    val cvv: String
)