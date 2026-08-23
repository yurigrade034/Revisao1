package sistema.servico
import repositorio.JPA

fun excluirServico() {
    val jpa = JPA()
    jpa.listarServico()
    println("Qual ID deseja excluir?")
    val id = readln().toInt()
    jpa.excluirServico(id)
}