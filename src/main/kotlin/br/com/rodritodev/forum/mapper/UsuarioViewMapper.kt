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
class UsuarioViewMapper: Mapper<Usuario, UsuarioView> {

    /**
     * Mapeia um Usuário para um UsuarioView
     * @param t Usuário
     * @return Visualização de usuário mapeada
     */
    override fun map(t: Usuario): UsuarioView {
        return UsuarioView(
            id = t.id,
            nome = t.nome,
            email = t.email,
            senha = t.senha.maskSenha(),
        )
    }
}