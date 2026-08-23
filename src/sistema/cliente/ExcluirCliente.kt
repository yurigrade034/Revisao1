package sistema.cliente
import repositorio.JPA

fun excluirCliente() {
    val jpa = JPA()
    jpa.listarCliente()
    println("Qual ID deseja excluir?")
    val id = readln().toInt()
    jpa.excluirCliente(id)
}