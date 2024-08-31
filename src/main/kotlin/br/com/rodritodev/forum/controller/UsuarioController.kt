package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.dto.AtualizacaoUsuarioForm
import br.com.rodritodev.forum.dto.NovoUsuarioForm
import br.com.rodritodev.forum.dto.UsuarioView
import br.com.rodritodev.forum.model.Usuario
import br.com.rodritodev.forum.service.UsuarioService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/usuarios")
class UsuarioController(private val usuarioService: UsuarioService) {

    /**
     * Lista todos os usuários ou filtra por nome do usuário
     * @return Lista de usuários
     */
    @GetMapping
    @Cacheable("usuariosEmCache") // Habilita o cache para a lista de usuários
    fun listar(
        @RequestParam(required = false) nome: String?,
        @PageableDefault(size = 10, sort = ["nome"]) paginacao: Pageable
    ): Page<UsuarioView> {
        return usuarioService.listar(nome, paginacao)
    }

    /**
     * Busca um usuário pelo id
     * @param id Id do usuário
     * @return Usuário encontrado
     */
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Usuario {
        return usuarioService.buscarPorId(id)
    }

    /**
     * Cadastra um novo usuário
     * @param usuario Usuário a ser cadastrado
     * @return Usuário cadastrado
     */
    @PostMapping
    @Transactional
    @CacheEvict(value = ["usuariosEmCache"], allEntries = true) // Limpa o cache de usuários ao cadastrar um novo usuário
    fun cadastrar(
        @RequestBody @Valid dto: NovoUsuarioForm,
        uriBuilder: UriComponentsBuilder,
    ): ResponseEntity<UsuarioView> {
        val usuario = usuarioService.cadastrar(dto)
        val uri = uriBuilder.path("/usuarios/${usuario.id}").build().toUri()
        return ResponseEntity.created(uri).body(usuario)
    }

    /**
     * Atualiza um usuário
     * @param usuario Usuário a ser atualizado
     * @return Usuário atualizado
     */
    @PutMapping
    @Transactional
    @CacheEvict(value = ["usuariosEmCache"], allEntries = true) // Limpa o cache de usuários ao atualizar um usuário
    fun atualizar(@RequestBody @Valid dto: AtualizacaoUsuarioForm): ResponseEntity<UsuarioView> {
        val usuario = usuarioService.atualizar(dto)
        return ResponseEntity.ok(usuario)
    }

    /**
     * Deleta um usuário
     * @param id Id do usuário
     */
    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = ["usuariosEmCache"], allEntries = true) // Limpa o cache de usuários ao deletar um usuário
    fun deletar(@PathVariable id: Long) {
        usuarioService.deletar(id)
    }
}