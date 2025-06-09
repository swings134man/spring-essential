package com.lucas.kopringjpademo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class KopringJpaDemoApplication

fun main(args: Array<String>) {
    runApplication<KopringJpaDemoApplication>(*args)
}
