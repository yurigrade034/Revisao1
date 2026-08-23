package sistema.fornecedor
import repositorio.JPA

fun excluirFornecedor() {
    val jpa = JPA()
    jpa.listarFornecedor()
    println("Qual ID deseja excluir?")
    val id = readln().toInt()
    jpa.excluirFornecedor(id)
}