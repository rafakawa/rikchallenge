package br.com.rik.rikchallenge.model

import org.junit.Assert.*
import org.junit.Test

class CardTest {

    @Test
    fun testValidCard() {
        // Arrange
        val card = Card("name1", "5555666677778884", "02/2030", "333")

        // Act
        val result = card.validate()

        // Assert
        assertTrue(result)
    }

    @Test
    fun testInvalidCard() {
        val card = Card("name1", "129738465", "02/2030", "333")

        // Act
        val result = card.validate()

        // Assert
        assertFalse(result)
    }

    @Test
    fun testValidBrand() {
        // Arrange
        val card = Card("name1", "5555666677778884", "02/2030", "333")

        // Act
        val result = card.getBrand()

        // Assert
        assertEquals(CardBrand.MASTERCARD, result)
    }

    @Test
    fun testInvalidBrand() {
        // Arrange
        val card = Card("name1", "129738465", "02/2030", "333")

        // Act
        val result = card.getBrand()

        // Assert
        assertEquals(CardBrand.UNKNOWN, result)
    }
}