package sistema.servico

import repositorio.JPA

fun listarServico() {
    val jpa = JPA()
    jpa.listarServico()
}