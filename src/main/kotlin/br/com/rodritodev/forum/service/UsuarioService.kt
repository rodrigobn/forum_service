package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.model.Usuario
import org.springframework.stereotype.Service

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
                nome = "João",
                email = "joao@mail.com",
            ),
        )
    }

    fun buscarPorId(id: Long): Usuario {
        usuarios.forEach {
            if (it.id == id) {
                return it
            }
        }
        throw IllegalArgumentException("Usuário ($id) não encontrado")
    }
}
