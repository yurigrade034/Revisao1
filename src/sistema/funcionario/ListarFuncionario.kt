package sistema.funcionario

import repositorio.JPA

fun listarFuncionario() {
    val jpa = JPA()
    jpa.listarFuncionario()
}