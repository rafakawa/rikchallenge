package br.com.rik.rikchallenge.card

import br.com.rik.rikchallenge.model.Card
import br.com.rik.rikchallenge.model.getBrand
import br.com.rik.rikchallenge.model.validate
import org.springframework.stereotype.Component
import ro.pippo.controller.Controller
import ro.pippo.controller.POST
import ro.pippo.controller.Path
import ro.pippo.controller.Produces
import ro.pippo.controller.extractor.Body

@Path("/card")
@Component
class CardController : Controller() {

    @POST("/validate")
    @Produces(Produces.JSON)
    fun validateCard(@Body cardControllerInput: CardControllerInput) : CardControllerOutput {

        val card = Card(cardControllerInput.holderName, cardControllerInput.number, cardControllerInput.expirationDate, cardControllerInput.cvv)

        val result = card.validate()

        val output = CardControllerOutput(result, card.getBrand().toString())

        return output
    }

}