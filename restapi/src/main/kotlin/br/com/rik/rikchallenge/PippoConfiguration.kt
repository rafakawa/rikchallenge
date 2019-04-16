package br.com.rik.rikchallenge

import org.springframework.beans.factory.ListableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ro.pippo.controller.Controller
import ro.pippo.controller.ControllerApplication
import ro.pippo.core.Pippo

@Configuration
class PippoConfiguration {

    @Bean
    open fun startPippo(beanFactory: ListableBeanFactory): Pippo {
        val controllers = beanFactory.getBeansOfType(Controller::class.java).values

        val app = object : ControllerApplication() {
            override fun onInit() {
                router.ignorePaths("/favicon.ico")
                controllers.forEach { addControllers(it) }
            }
        }

        val pippo = Pippo(app)
        pippo.start()

        return pippo
    }

}