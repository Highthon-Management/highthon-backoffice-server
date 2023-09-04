package com.example.highthon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class HighthonBackofficeServerApplicationKt

fun main(args: Array<String>) {
    runApplication<HighthonBackofficeServerApplicationKt>(*args)
}
