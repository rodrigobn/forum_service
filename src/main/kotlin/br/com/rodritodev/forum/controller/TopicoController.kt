package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.dto.AtualizacaoTopicoForm
import br.com.rodritodev.forum.dto.NovoTopicoForm
import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.service.TopicoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Controlador de tópicos do fórum
 */
@RestController
@RequestMapping("/topicos")
class TopicoController(private val topicoService: TopicoService) {

    /**
     * Lista todos os tópicos ou filtra por nome do curso
     * @return Lista de tópicos
     */
    @GetMapping
    fun listar(
        @RequestParam(required = false) nomeCurso: String?,
        @PageableDefault(size = 10, sort = ["titulo"], direction = Sort.Direction.DESC) paginacao: Pageable
    ): Page<TopicoView> {
        return topicoService.listar(nomeCurso, paginacao)
    }

    /**
     * Busca um tópico pelo id
     * @param id Id do tópico
     * @return Tópico encontrado
     */
    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return topicoService.buscarPorId(id)
    }

    /**
     * Cadastra um novo tópico
     * @param dto Dados do tópico
     * @return Tópico cadastrado e URI do novo tópico
     */
    @PostMapping
    @Transactional
    fun cadastrar(
        @RequestBody @Valid dto: NovoTopicoForm,
        uriBuider: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        val topicoView = topicoService.cadastrar(dto)

        // Cria a URI do novo tópico criado para ser retornada no cabeçalho Location
        val uri = uriBuider.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    /**
     * Atualiza um tópico
     * @param dto Dados do tópico
     * @return Tópico atualizado
     */
    @PutMapping
    @Transactional
    fun atualizar(@RequestBody @Valid dto: AtualizacaoTopicoForm): ResponseEntity<TopicoView> {
        val topicoView = topicoService.atualizar(dto)
        return ResponseEntity.ok(topicoView)
    }

    /**
     * Deleta um tópico
     * @param id Id do tópico
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun deletar(@PathVariable id: Long) {
        topicoService.deletar(id)
    }
}