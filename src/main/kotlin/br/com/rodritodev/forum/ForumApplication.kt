package br.com.rodritodev.forum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication // Indica que a classe é uma classe de configuração do Spring Boot
@EnableCaching // Habilita o uso de cache na aplicação
class ForumApplication

fun main(args: Array<String>) {
	runApplication<ForumApplication>(*args)
}
