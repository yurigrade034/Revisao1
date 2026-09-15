package sistema.caixadaagua

import repository.CRUDCaixaDaAgua
import sistema.util.lerInteiro

fun excluirCaixa() {
    val conexao = CRUDCaixaDaAgua()
    conexao.listar()

    val id = lerInteiro("Qual Caixa excluir, ID? ")

    conexao.excluir(id)
}
