package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.model.Credentials
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/login")
class LoginController {

    @PostMapping
    @Operation(
        summary = "Login",
        description = "Autentica um usuário e retorna um JWT.",
        responses = [
            ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
            ApiResponse(responseCode = "401", description = "Credenciais inválidas")
        ],
    )
    fun login(
        @RequestBody @Valid dto: Credentials,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Void> {
        return ResponseEntity.ok().build() //  Return an empty response
    }
}