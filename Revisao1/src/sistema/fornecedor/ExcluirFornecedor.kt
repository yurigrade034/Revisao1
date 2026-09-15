package sistema.fornecedor

import repository.CRUDFornecedor
import sistema.util.lerInteiro

fun excluirFornecedor() {
    val conexao = CRUDFornecedor()
    conexao.listar()

    val id = lerInteiro("Qual fornecedor excluir, ID? ")

    conexao.excluir(id)
}
