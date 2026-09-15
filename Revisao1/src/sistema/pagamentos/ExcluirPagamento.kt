package sistema.pagamentos

import repository.CRUDMovimentacao
import sistema.util.lerInteiro

fun excluirPagamento() {
    val conexao = CRUDMovimentacao()
    conexao.listar()

    val id = lerInteiro("Qual movimentação excluir, ID? ")

    conexao.excluir(id)
}
