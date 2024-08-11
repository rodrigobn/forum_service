package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.dto.NovoTopicoForm
import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.service.TopicoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
     */
    @PostMapping
    fun cadastrar(@RequestBody dto: NovoTopicoForm) {
        topicoService.cadastrar(dto)
    }
}