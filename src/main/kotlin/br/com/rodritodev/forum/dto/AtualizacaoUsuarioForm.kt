package br.com.rodritodev.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AtualizacaoUsuarioForm(
    @field:NotNull val id: Long,
    @field:NotEmpty @Size(min = 5, max = 150) val nome: String,
    @field:NotEmpty val email: String,
)