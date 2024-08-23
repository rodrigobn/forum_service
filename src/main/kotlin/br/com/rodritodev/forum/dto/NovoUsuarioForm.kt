package br.com.rodritodev.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

/**
 * Formulário de criação de um novo usuário
 *
 * Form é um dto que chega na aplicação
 * @property nome Nome do usuário
 * @property email Email do usuário
 * @property senha Senha do usuário
 *
 */
data class NovoUsuarioForm(
    @field:NotEmpty(message = "O nome não pode ser em branco")
    @field: Size(min = 5, max = 150, message = "O nome deve ter entre 5 e 150 caracteres")
    val nome: String,
    @field:NotEmpty(message = "O email não pode ser em branco")
    val email: String,
    @field:NotEmpty(message = "A senha não pode ser em branco")
    @field:Size(min = 4, max = 12, message = "A senha deve ter entre 4 e 12 caracteres")
    val senha: String,
)
