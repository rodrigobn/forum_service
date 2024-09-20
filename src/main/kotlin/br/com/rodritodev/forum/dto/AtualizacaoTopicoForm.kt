package br.com.rodritodev.forum.dto

import br.com.rodritodev.forum.model.Resposta
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * Formulário de atualização de um tópico no fórum
 *
 * Form é um dto que chega na aplicação
 */
data class AtualizacaoTopicoForm(
    @field:NotNull val id: Long,
    @field:NotEmpty @Size(min = 5, max = 150) val titulo: String,
    @field:NotEmpty val mensagem: String,
    val respostas: List<RespostaView>,
)