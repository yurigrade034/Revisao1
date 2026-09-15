package sistema.venda

import repository.CRUDVenda

fun listarVenda() {
    val conexao = CRUDVenda()
    conexao.listar()
}
