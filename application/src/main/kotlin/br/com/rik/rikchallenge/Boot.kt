package br.com.rik.rikchallenge

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = arrayOf("br.com.rik.rikchallenge"))
class Boot {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            AnnotationConfigApplicationContext(Boot::class.java)
        }
    }

}