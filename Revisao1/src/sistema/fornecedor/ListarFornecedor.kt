package sistema.fornecedor

import repository.CRUDFornecedor

fun listarFornecedor() {
    val conexao = CRUDFornecedor()
    conexao.listar()
}
