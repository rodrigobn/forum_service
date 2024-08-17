package br.com.rodritodev.forum.mapper

import br.com.rodritodev.forum.dto.TopicoView
import br.com.rodritodev.forum.model.Topico
import org.springframework.stereotype.Component

/**
 * Mapper de tópicos para visualização
 *
 * Mapper é uma classe que transforma um objeto em outro
 */
@Component
class TopicoViewMapper: Mapper<Topico, TopicoView> {

    /**
     * Mapeia um Topico para um TopicoView
     * @param t Tópico
     * @return Visualização de tópico mapeada
     */
    override fun map(t: Topico): TopicoView {
        return TopicoView(
            id = t.id,
            titulo = t.titulo,
            mensagem = t.mensagem,
            status = t.status,
            dataCriacao = t.dataCriacao,
            respostas = t.resposta
        )
    }
}