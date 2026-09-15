package sistema.estoque

import repository.CRUDEstoque

fun listarEstoque() {
    val conexao = CRUDEstoque()
    conexao.listar()
}
