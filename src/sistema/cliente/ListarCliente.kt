package sistema.cliente

import repositorio.JPA

fun listarCliente() {
    val jpa = JPA()
    jpa.listarCliente()
}