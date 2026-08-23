package sistema.fornecedor

import repositorio.JPA

fun listarFornecedor() {
    val jpa = JPA()
    jpa.listarFornecedor()
}