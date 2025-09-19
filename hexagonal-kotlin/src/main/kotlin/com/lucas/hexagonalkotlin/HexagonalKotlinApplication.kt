package com.lucas.hexagonalkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class HexagonalKotlinApplication

fun main(args: Array<String>) {
    runApplication<HexagonalKotlinApplication>(*args)
}
