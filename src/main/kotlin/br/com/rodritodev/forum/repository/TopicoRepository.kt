package br.com.rodritodev.forum.repository

import br.com.rodritodev.forum.model.Topico
import org.springframework.data.jpa.repository.JpaRepository

interface TopicoRepository: JpaRepository<Topico, Long>