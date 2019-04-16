package br.com.rik.rikchallenge.card

data class CardControllerInput (
    val holderName: String,
    val number: String,
    val expirationDate: String,
    val cvv: String
)