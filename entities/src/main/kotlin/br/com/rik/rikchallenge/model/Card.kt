package br.com.rik.rikchallenge.model

import br.com.moip.creditcard.Brands
import br.com.moip.validators.CreditCard

data class Card(
    val holderName: String,
    val number: String,
    val expirationDate: String,
    val cvv: String
)

fun Card.validate() : Boolean {
    return CreditCard(this.number).isValid
}

// Outside entities, I should work only with my types,
// so if something changes at the third party lib, it doesn't break my application code
fun Card.getBrand() : CardBrand = when {
    CreditCard(this.number).brand == Brands.AMERICAN_EXPRESS -> CardBrand.AMERICAN_EXPRESS
    CreditCard(this.number).brand == Brands.DINERS -> CardBrand.DINERS
    CreditCard(this.number).brand == Brands.ELO -> CardBrand.ELO
    CreditCard(this.number).brand == Brands.HIPER -> CardBrand.HIPER
    CreditCard(this.number).brand == Brands.HIPERCARD -> CardBrand.HIPERCARD
    CreditCard(this.number).brand == Brands.MASTERCARD -> CardBrand.MASTERCARD
    CreditCard(this.number).brand == Brands.VISA -> CardBrand.VISA
    CreditCard(this.number).brand == Brands.UNKNOWN -> CardBrand.UNKNOWN
    else -> CardBrand.UNKNOWN
}