package sistema.instalador

import repositorio.JPA

fun listarInstalador() {
    val jpa = JPA()
    jpa.listarInstalador()
}