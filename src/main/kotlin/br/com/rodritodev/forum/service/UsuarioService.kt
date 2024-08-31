package br.com.rodritodev.forum.service

import br.com.rodritodev.forum.dto.AtualizacaoUsuarioForm
import br.com.rodritodev.forum.dto.NovoUsuarioForm
import br.com.rodritodev.forum.dto.UsuarioView
import br.com.rodritodev.forum.exception.NotFoundException
import br.com.rodritodev.forum.exception.UsuarioJaCadastradoException
import br.com.rodritodev.forum.extensions.maskEmail
import br.com.rodritodev.forum.mapper.UsuarioMapper
import br.com.rodritodev.forum.mapper.UsuarioViewMapper
import br.com.rodritodev.forum.model.Usuario
import br.com.rodritodev.forum.repository.UsuarioRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
     * Retorna uma lista de usuários ou filtra por nome
     * @return Lista de usuários
     */
    fun listar(nome: String?, paginacao: Pageable): Page<UsuarioView> {
        if (nome != null) {
            return repository.findByNomeContaining(nome, paginacao).map(usuarioViewMapper::map)
        }
        return repository.findAll(paginacao).map(usuarioViewMapper::map)
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
        repository.findById(id).orElseThrow {
            NotFoundException("Usuário ($id) não encontrado")
        }
        repository.deleteById(id)
    }
}
