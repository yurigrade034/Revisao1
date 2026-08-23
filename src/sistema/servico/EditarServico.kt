package sistema.servico

import repositorio.JPA
import java.time.LocalDate

fun editarServico() {
    val jpa = JPA()
    jpa.listarServico()

    println("Digite o ID do serviço que deseja editar")
    val id = readln().toInt()

    println("Clientes cadastrados:")
    jpa.listarCliente()
    println("Digite o novo ID do cliente: ")
    val idCliente = readln().toInt()

    println("Instaladores cadastrados:")
    jpa.listarInstalador()
    println("Digite o novo ID do instalador: ")
    val idInstalador = readln().toInt()

    println("Digite o novo preço do serviço: ")
    val preco = readln()

    println("Digite a nova data da instalação (aaaa-mm-dd): ")
    val dataInstalacao = LocalDate.parse(readln())

    jpa.editarServico(preco, dataInstalacao, idCliente, idInstalador, id)
}