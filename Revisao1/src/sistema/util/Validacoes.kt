package sistema.util

import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeParseException

//Aceita CPF só números (11 dígitos) ou formatado (000.000.000-00)
val regexCpf = Regex("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$|^\\d{11}$")

fun lerCpfValido(mensagem: String): String {
    while (true) {
        println(mensagem)
        val cpf = readln()
        if (regexCpf.matches(cpf)) {
            return cpf
        }
        println("CPF inválido! Use o formato 000.000.000-00 ou 11 números seguidos.")
    }
}

//Leitura segura de número inteiro: não deixa o programa quebrar se o usuário
//digitar algo que não seja um número (ou apertar Enter sem digitar nada).
fun lerInteiro(mensagem: String): Int {
    while (true) {
        println(mensagem)
        val entrada = readln()
        try {
            return entrada.toInt()
        } catch (e: NumberFormatException) {
            println("Valor inválido! Digite um número inteiro (ex: 3).")
        }
    }
}

//Leitura segura de valor monetário/decimal.
fun lerDecimal(mensagem: String): BigDecimal {
    while (true) {
        println(mensagem)
        val entrada = readln()
        try {
            return entrada.toBigDecimal()
        } catch (e: NumberFormatException) {
            println("Valor inválido! Digite um número (ex: 150.50).")
        }
    }
}

//Leitura segura de número com casas decimais (ex: dimensões em metros).
fun lerDouble(mensagem: String): Double {
    while (true) {
        println(mensagem)
        val entrada = readln()
        try {
            return entrada.toDouble()
        } catch (e: NumberFormatException) {
            println("Valor inválido! Digite um número (ex: 1.5).")
        }
    }
}

//Leitura segura de data no formato AAAA-MM-DD.
fun lerData(mensagem: String): LocalDate {
    while (true) {
        println(mensagem)
        val entrada = readln()
        try {
            return LocalDate.parse(entrada)
        } catch (e: DateTimeParseException) {
            println("Data inválida! Use o formato AAAA-MM-DD (ex: 2025-01-30).")
        }
    }
}

//Leitura de texto simples, mas nunca aceita vazio (Enter sem digitar nada).
fun lerTexto(mensagem: String): String {
    while (true) {
        println(mensagem)
        val entrada = readln()
        if (entrada.isNotBlank()) {
            return entrada
        }
        println("Esse campo não pode ficar vazio!")
    }
}

//Mostra as opções de um enum, lê o número escolhido e valida se está dentro do range.
fun <T : Enum<T>> lerOpcaoEnum(mensagem: String, valores: Array<T>): T {
    println(mensagem)
    valores.forEach { valor -> println("${valor.ordinal} - ${valor.name}") }
    while (true) {
        val indice = lerInteiro("Número da opção: ")
        if (indice in valores.indices) {
            return valores[indice]
        }
        println("Opção inválida! Escolha um número entre 0 e ${valores.size - 1}.")
    }
}
