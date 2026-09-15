package sistema.compra

import repository.CRUDCompra
import sistema.util.lerInteiro

fun excluirCompra() {
    val conexao = CRUDCompra()
    conexao.listar()

    val id = lerInteiro("Qual compra excluir, ID? ")

    conexao.excluir(id)
}
