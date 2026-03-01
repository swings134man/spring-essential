package com.lucas.stompdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StompDemoApplication

fun main(args: Array<String>) {
    runApplication<StompDemoApplication>(*args)
}
