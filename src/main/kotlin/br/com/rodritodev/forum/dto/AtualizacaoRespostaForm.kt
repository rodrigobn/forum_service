package br.com.rodritodev.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AtualizacaoRespostaForm(
    @field:NotNull val idResposta: Long,
    @field:NotNull val idTopico: Long,
    @field:NotEmpty @Size(min = 5, max = 200) val mensagem: String,
    val solucao: Boolean? = false
)