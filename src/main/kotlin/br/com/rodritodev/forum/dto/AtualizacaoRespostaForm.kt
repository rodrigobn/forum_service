package br.com.rodritodev.forum.dto

import br.com.rodritodev.forum.model.Resposta

data class AtualizacaoRespostaForm(
    val idResposta: Long,
    val idTopico: Long,
    val mensagem: String,
    val solucao: Boolean? = false
)