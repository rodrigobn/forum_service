package br.com.rodritodev.forum.mapper

import br.com.rodritodev.forum.dto.NovaRespostaForm
import br.com.rodritodev.forum.model.Resposta
import org.springframework.stereotype.Component

/**
 * Mapper de formulário de resposta para entidade de resposta
 *
 * Mapper é uma classe que transforma um objeto em outro
 */
@Component
class RespostaFormMapper : Mapper<NovaRespostaForm, Resposta> {
    override fun map(resposta: NovaRespostaForm): Resposta {
        return Resposta(
            mensagem = resposta.mensagem,
            solucao = false,
        )
    }
}
