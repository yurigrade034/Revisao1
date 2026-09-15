package sistema.caixadaagua

import repository.CRUDCaixaDaAgua

fun listarCaixa() {
    val conexao = CRUDCaixaDaAgua()
    conexao.listar()
}
