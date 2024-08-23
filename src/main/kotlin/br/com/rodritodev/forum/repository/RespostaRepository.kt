package br.com.rodritodev.forum.repository

import br.com.rodritodev.forum.model.Resposta
import org.springframework.data.jpa.repository.JpaRepository

interface RespostaRepository: JpaRepository<Resposta, Long>