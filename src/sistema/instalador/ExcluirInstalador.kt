package sistema.instalador
import repositorio.JPA

fun excluirInstalador() {
    val jpa = JPA()
    jpa.listarInstalador()
    println("Qual ID deseja excluir?")
    val id = readln().toInt()
    jpa.excluirInstalador(id)
}