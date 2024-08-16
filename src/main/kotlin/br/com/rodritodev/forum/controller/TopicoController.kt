package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.dto.AtualizacaoTopicoForm
import br.com.rodritodev.forum.dto.NovoTopicoForm
import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.service.TopicoService
import jakarta.validation.Valid
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
     * Lista todos os tópicos
     * @return Lista de tópicos
     */
    @GetMapping
    fun listar(): List<TopicoView> {
        return topicoService.listar()
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
    fun atualizar(@RequestBody @Valid dto: AtualizacaoTopicoForm): ResponseEntity<TopicoView>  {
        val topicoView = topicoService.atualizar(dto)
        return ResponseEntity.ok(topicoView)
    }

    /**
     * Deleta um tópico
     * @param id Id do tópico
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Long) {
        topicoService.deletar(id)
    }
}