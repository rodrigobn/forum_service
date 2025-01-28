package br.com.rodritodev.forum.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val javaMailSender: JavaMailSender
) {
    fun notificar(
        destinatario: String,
        assunto: String,
        corpo: String
    ) {
        // Implementação do envio de e-mail
        val message = SimpleMailMessage() // Cria uma mensagem de e-mail
        message.setTo(destinatario) // Define o destinatário do e-mail
        message.setSubject(assunto) // Define o assunto do e-mail
        message.setText(corpo) // Define o corpo do e-mail
        javaMailSender.send(message) // Envia o e-mail
    }
}