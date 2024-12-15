package com.es.gameStore

import com.es.gameStore.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class GameStoreApplication

fun main(args: Array<String>) {
	runApplication<GameStoreApplication>(*args)
}
