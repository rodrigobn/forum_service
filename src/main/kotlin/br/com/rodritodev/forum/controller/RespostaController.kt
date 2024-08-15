package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.model.Resposta
import br.com.rodritodev.forum.service.RespostaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador de respostas dos tópicos do fórum
 */
@RestController
@RequestMapping("/respostas")
class RespostaController(private val respostaService: RespostaService) {

    @GetMapping("/topicos/{id}/respostas")
    fun listar(@PathVariable id: Long): List<Resposta> {
        return respostaService.listar(id)
    }
}