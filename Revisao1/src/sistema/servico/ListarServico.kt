package sistema.servico

import repository.CRUDServico

fun listarServico() {
    val conexao = CRUDServico()
    conexao.listar()
}
