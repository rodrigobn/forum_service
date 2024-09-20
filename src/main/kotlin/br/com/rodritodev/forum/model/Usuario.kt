package br.com.rodritodev.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

/**
 * Modelo de usuário
 *
 * @property id Identificador do usuário
 * @property nome Nome do usuário
 * @property email Email do usuário
 * @property senha Senha do usuário
 * @property roles Lista de roles do usuário
 * @constructor Cria um usuário
 */
@Entity
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var nome: String,
    var email: String,
    var senha: String,

    @JsonIgnore // Ignora a serialização do campo roles
    @ManyToMany(fetch = FetchType.EAGER) // Eager para carregar as roles junto com o usuário
    @JoinTable(
        name = "usuario_role", // Nome da tabela de junção
        joinColumns = [JoinColumn(name = "usuario_id")], // Chave estrangeira para a tabela Usuario
        inverseJoinColumns = [JoinColumn(name = "role_id")] // Chave estrangeira para a tabela Role
    )
    var roles: List<Role> = mutableListOf()
)