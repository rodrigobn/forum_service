package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.dto.NovaRespostaForm
import br.com.rodritodev.forum.dto.RespostaView
import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.model.Resposta
import br.com.rodritodev.forum.service.RespostaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

/**
 * Controlador de respostas dos tópicos do fórum
 */
@RestController
@RequestMapping("/respostas")
class RespostaController(private val respostaService: RespostaService) {

    /**
     * Lista as respostas de um tópico
     * @param id Id do tópico
     * @return Lista de respostas
     */
    @GetMapping("/topico/{id}")
    fun listar(@PathVariable id: Long): List<RespostaView> {
        return respostaService.listar(id)
    }

    /**
     * Cadastra uma nova resposta
     * @param resposta Dados da resposta
     * @return Resposta cadastrada e URI da nova resposta
     */
    @PostMapping
    fun cadastrar(
        @RequestBody resposta: NovaRespostaForm,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<RespostaView> {
        val respostaCadastrada = respostaService.cadastrar(resposta)

        val uri = uriComponentsBuilder.path("/respostas/${respostaCadastrada.id}").build().toUri()
        return ResponseEntity.created(uri).body(respostaCadastrada)
    }
}