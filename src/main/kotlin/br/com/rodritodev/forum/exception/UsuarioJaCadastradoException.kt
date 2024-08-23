package br.com.rodritodev.forum.exception

class UsuarioJaCadastradoException(email: String): RuntimeException("Usuário (${email}) já cadastrado") {
}