package br.com.rodritodev.forum.dto

/**
 * Visualização de um usuário
 *
 * View é um dto que sai da aplicação
 */
data class UsuarioView(
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
)