package br.com.rodritodev.forum.model

/**
 * Modelo de usuário
 */
data class Usuario(
    val id: Long? = null,
    val nome: String,
    val email: String,
)