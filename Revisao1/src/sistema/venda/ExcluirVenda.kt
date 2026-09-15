package sistema.venda

import repository.CRUDVenda
import sistema.util.lerInteiro

fun excluirVenda() {
    val conexao = CRUDVenda()
    conexao.listar()

    val id = lerInteiro("Qual venda excluir, ID? ")

    conexao.excluir(id)
}
