package com.vk.oed.slaver

import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SlaverApplication

fun main(args: Array<String>) {
	runBlocking { runApplication<SlaverApplication>(*args) }
}
