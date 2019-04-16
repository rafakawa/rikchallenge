package br.com.rik.rikchallenge.order

import org.springframework.stereotype.Component
import ro.pippo.controller.Controller
import ro.pippo.controller.POST
import ro.pippo.controller.Path
import ro.pippo.controller.Produces
import ro.pippo.controller.extractor.Body

@Path("/order")
@Component
class OrderController(val processOrder: ProcessOrder) : Controller() {

    @POST("/process")
    @Produces(Produces.JSON)
    fun processOrder(@Body processOrderRequestInput: ProcessOrderRequestInput) : ProcessOrderOutput {

        val paymentOrder = processOrderRequestInput.MapToPaymentOrder()

        val result = processOrder.process(paymentOrder)

        return result
    }

}
