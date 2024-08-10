package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.dto.NovoTopicoDto
import br.com.rodritodev.forum.model.Topico
import br.com.rodritodev.forum.service.TopicoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/topicos")
class TopicoController(private val topicoService: TopicoService) {

    @GetMapping
    fun listar(): List<Topico> {
        return topicoService.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Topico {
        return topicoService.buscarPorId(id)
    }

    @PostMapping
    fun cadastrar(@RequestBody dto: NovoTopicoDto) {
        topicoService.cadastrar(dto)
    }
}