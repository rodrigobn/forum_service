package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.dto.AtualizacaoUsuarioForm
import br.com.rodritodev.forum.dto.NovoUsuarioForm
import br.com.rodritodev.forum.dto.UsuarioView
import br.com.rodritodev.forum.exception.UsuarioJaCadastradoException
import br.com.rodritodev.forum.extensions.maskEmail
import br.com.rodritodev.forum.mapper.UsuarioMapper
import br.com.rodritodev.forum.mapper.UsuarioViewMapper
import br.com.rodritodev.forum.model.Usuario
import br.com.rodritodev.forum.repository.UsuarioRepository
import org.springframework.stereotype.Service

/**
 * Serviço de usuários do fórum
 */
@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    private val usuarioViewMapper: UsuarioViewMapper,
    private val usuarioMapper: UsuarioMapper,
) {

    /**
     * Retorna uma lista de usuários
     * @return Lista de usuários
     */
    fun listar(): List<UsuarioView> {
        return repository.findAll().map(usuarioViewMapper::map).toList()
    }

    /**
     * Busca um usuário pelo id
     * @param id Id do usuário
     * @return Usuário encontrado
     */
    fun buscarPorId(id: Long): Usuario {
        val usuario = repository.findById(id).orElseThrow {
            Exception("Usuário (${id}) não encontrado")
        }
        // TODO - Implementar mapper para Usuario sem exibir a senha
        return usuarioMapper.map(usuarioViewMapper.map(usuario))
    }

    /**
     * Cadastra um novo usuário no fórum caso o email ainda não esteja cadastrado
     * @param usuario Usuário a ser cadastrado
     * @return Usuário cadastrado
     */
    fun cadastrar(dto: NovoUsuarioForm): UsuarioView {
        val usuario = Usuario(
            nome = dto.nome,
            email = dto.email,
            senha = dto.senha,
        )
        if (repository.existsByEmail(usuario.email)) {
            throw UsuarioJaCadastradoException(usuario.email.maskEmail())
        }
        return usuarioViewMapper.map(repository.save(usuario))
    }

    /**
     * Atualiza um usuário
     * @param usuario Usuário a ser atualizado
     * @return Usuário atualizado
     */
    fun atualizar(atualizacaoUsuarioForm: AtualizacaoUsuarioForm): UsuarioView {
        val usuario = repository.findById(atualizacaoUsuarioForm.id).orElseThrow {
            Exception("Usuário (${atualizacaoUsuarioForm.id}) não encontrado")
        }

        usuario.nome = atualizacaoUsuarioForm.nome
        usuario.email = atualizacaoUsuarioForm.email

        return usuarioViewMapper.map(repository.save(usuario))
    }

    /**
     * Deleta um usuário
     * @param id Id do usuário
     */
    fun deletar(id: Long) {
        repository.deleteById(id)
    }
}
