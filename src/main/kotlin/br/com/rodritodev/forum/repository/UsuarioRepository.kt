package br.com.rodritodev.forum.repository

import br.com.rodritodev.forum.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long>{
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Usuario?
    fun findByNome(nome: String): Usuario?
    fun findByNomeAndEmail(nome: String, email: String): Usuario?
    fun findByNomeOrEmail(nome: String, email: String): Usuario?
}