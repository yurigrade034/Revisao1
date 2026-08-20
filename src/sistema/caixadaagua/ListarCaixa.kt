package sistema.caixadaagua

import repositorio.JPA


fun listarCaixa(){
    val jpa = JPA()
    jpa.listar()
}
