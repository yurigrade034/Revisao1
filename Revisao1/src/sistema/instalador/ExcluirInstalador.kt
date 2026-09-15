package sistema.instalador

import repository.CRUDInstalador
import sistema.util.lerInteiro

fun excluirInstalador() {
    val conexao = CRUDInstalador()
    conexao.listar()

    val id = lerInteiro("Qual instalador excluir, ID? ")

    conexao.excluir(id)
}
