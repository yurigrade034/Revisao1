package sistema.servico

import repository.CRUDServico
import sistema.util.lerInteiro

fun excluirServico() {
    val conexao = CRUDServico()
    conexao.listar()

    val id = lerInteiro("Qual serviço excluir, ID? ")

    conexao.excluir(id)
}
