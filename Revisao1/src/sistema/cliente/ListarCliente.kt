package sistema.cliente

import repository.CRUDCliente

fun listarCliente() {
    val conexao = CRUDCliente()
    conexao.listar()
}
