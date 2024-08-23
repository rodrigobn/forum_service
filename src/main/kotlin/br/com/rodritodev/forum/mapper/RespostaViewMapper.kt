package br.com.rodritodev.forum.mapper

import br.com.rodritodev.forum.dto.RespostaView
import br.com.rodritodev.forum.model.Resposta
import org.springframework.stereotype.Component

/**
 * Mapper de respostas para visualização
 *
 * Mapper é uma classe que transforma um objeto em outro
 */
@Component
class RespostaViewMapper: Mapper<Resposta, RespostaView> {

    override fun map(t: Resposta): RespostaView {
        return RespostaView(
            id = t.id,
            mensagem = t.mensagem,
            dataCriacao = t.dataCriacao,
            nomeAutor = t.usuario?.nome ?: "Desconhecido",
            tituloTopico = t.topico?.titulo ?: "Desconhecido",
            solucao = t.solucao,
        )
    }

}
