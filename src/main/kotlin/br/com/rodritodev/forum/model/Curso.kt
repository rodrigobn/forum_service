package br.com.rodritodev.forum.model

/**
 * Modelo de um curso
 */
data class Curso(
    val id: Long? = null,
    val nome: String,
    val categoria: String,
)
