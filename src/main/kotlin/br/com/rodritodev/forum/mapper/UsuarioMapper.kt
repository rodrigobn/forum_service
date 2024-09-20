package br.com.rodritodev.forum.mapper

import br.com.rodritodev.forum.dto.UsuarioView
import br.com.rodritodev.forum.extensions.maskSenha
import br.com.rodritodev.forum.model.Usuario
import org.springframework.stereotype.Component

/**
 * Mapper de usuário para visualização
 *
 * Mapper é uma classe que transforma um objeto em outro
 */
@Component
class UsuarioMapper : Mapper<UsuarioView, Usuario> {

    /**
     * Mapeia um UsuarioView para um Usuario
     * @param t Usuário
     * @return Visualização de usuário mapeada
     */
    override fun map(t: UsuarioView): Usuario {
        return Usuario(
            id = t.id,
            nome = t.nome,
            email = t.email,
            senha = t.senha.maskSenha(),
        )
    }
}