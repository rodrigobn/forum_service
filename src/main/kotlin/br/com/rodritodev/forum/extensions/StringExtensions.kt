package br.com.rodritodev.forum.extensions

/**
 * Mascara um email, substituindo parte do nome do usuário e do domínio por asteriscos
 * @return Email mascarado
 */
fun String.maskEmail(): String {
    // Verifica se a string é um email válido
    val emailParts = this.split("@")
    if (emailParts.size != 2) return this // Retorna a string original se não for um email válido

    val username = emailParts[0]
    val domain = emailParts[1]

    // Mascara o nome do usuário e o domínio
    val maskedUsername = if (username.length > 2) {
        username.first() + "*".repeat(username.length - 2) + username.last()
    } else {
        "*".repeat(username.length)
    }

    val domainParts = domain.split(".")
    val maskedDomain = domainParts.joinToString(".") { part ->
        if (part.length > 2) {
            part.first() + "*".repeat(part.length - 2) + part.last()
        } else {
            "*".repeat(part.length)
        }
    }

    return "$maskedUsername@$maskedDomain"
}

fun String.maskSenha(): String {
    return this.replace(Regex("[a-zA-Z0-9]"), "*")
}