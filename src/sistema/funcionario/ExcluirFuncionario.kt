package sistema.funcionario
import repositorio.JPA

fun excluirFuncionario() {
    val jpa = JPA()
    jpa.listarFuncionario()
    println("Qual ID deseja excluir?")
    val id = readln().toInt()
    jpa.excluirFuncionario(id)
}