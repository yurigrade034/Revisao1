package sistema.instalador

import repository.CRUDInstalador

fun listarInstalador() {
    val conexao = CRUDInstalador()
    conexao.listar()
}
