package sistema.cliente

import repository.CRUDCliente
import sistema.util.lerInteiro

fun excluirCliente() {
    val conexao = CRUDCliente()
    conexao.listar()

    val id = lerInteiro("Qual cliente excluir, ID? ")

    conexao.excluir(id)
}
