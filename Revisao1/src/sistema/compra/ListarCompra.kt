package sistema.compra

import repository.CRUDCompra

fun listarCompra() {
    val conexao = CRUDCompra()
    conexao.listar()
}
