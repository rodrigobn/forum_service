package br.com.rodritodev.forum.controller

import br.com.rodritodev.forum.service.TopicoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("relatorios")
class RelatorioController(
    private val topicoService: TopicoService
) {


    /**
     * Relatório de tópicos por categoria
     * @return Lista de tópicos por categoria
     */
    @GetMapping
    fun relatorio(model: Model): String {
        model.addAttribute("topicosPorCategorias", topicoService.relatorio())
        return "relatorio"
    }
}