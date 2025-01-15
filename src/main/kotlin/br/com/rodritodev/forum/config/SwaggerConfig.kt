package br.com.rodritodev.forum.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.Paths
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Classe de configuração do Swagger
 *
 * @author Rodrigo
 * @version 1.0.0
 */
@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class SwaggerConfig {
    @Bean
    fun openApiCustomizer(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi ->
            val sortedPaths = openApi.paths.entries
                .sortedWith(compareBy {
                    when (it.key) {
                        "/login" -> 0 // Garante que /login seja o primeiro
                        else -> 1     // Todos os outros depois
                    }
                })

            // Cria um novo objeto Paths e adiciona os itens ordenados
            val newPaths = Paths()
            sortedPaths.forEach { (key, value) ->
                newPaths.addPathItem(key, value)
            }

            // Substitui os paths existentes pelo novo objeto ordenado
            openApi.paths = newPaths
        }
    }
}