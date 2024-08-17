package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.model.Usuario
import org.springframework.stereotype.Service

/**
 * Serviço de usuários do fórum
 */
@Service
class UsuarioService(var usuarios: List<Usuario>) {

    init {
        usuarios = listOf(
            Usuario(
                id = 1,
                nome = "Rodrigo",
                email = "rodrigo@mail.com",
            ),
            Usuario(
                id = 2,
                nome = "Aline",
                email = "aline@mail.com",
            ),
        )
    }

    /**
     * Busca um usuário pelo id
     * @param id Id do usuário
     * @return Usuário encontrado
     */
    fun buscarPorId(id: Long): Usuario {
        usuarios.forEach {
            if (it.id == id) {
                return it
            }
        }
        throw IllegalArgumentException("Usuário ($id) não encontrado")
    }
}
