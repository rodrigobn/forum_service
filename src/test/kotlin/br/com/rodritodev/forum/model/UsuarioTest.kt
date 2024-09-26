package br.com.rodritodev.forum.model

object UsuarioTest {
    fun buildToToken(): Usuario {
        return Usuario(
            id = 1,
            nome = "Admin",
            email = "admin@mail.com",
            senha = "\$2a\$06\$C26JaGb5korSUE7ecCBMFu1jxSuGZ9gLLrff0wlyT00tzt9iRHrei",
            roles = listOf(Role(1, "ROLE_ADMIN"))
        )
    }
}