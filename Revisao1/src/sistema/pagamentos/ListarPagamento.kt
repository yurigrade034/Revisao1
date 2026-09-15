package sistema.pagamentos

import repository.CRUDMovimentacao

fun listarPagamento() {
    val CRUDMovimentacao = CRUDMovimentacao()
    CRUDMovimentacao.listar()
}
