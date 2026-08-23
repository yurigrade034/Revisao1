package sistema.servico

import repositorio.JPA
import java.time.LocalDate

fun cadastrarNovoServico() {
    val jpa = JPA()

    println("Clientes cadastrados:")
    jpa.listarCliente()
    println("Digite o ID do cliente: ")
    val idCliente = readln().toInt()

    println("Instaladores cadastrados:")
    jpa.listarInstalador()
    println("Digite o ID do instalador: ")
    val idInstalador = readln().toInt()

    println("Digite o preço do serviço: ")
    val preco = readln()

    println("Digite a data da instalação (aaaa-mm-dd): ")
    val dataInstalacao = LocalDate.parse(readln())

    jpa.salvarServico(preco, dataInstalacao, idCliente, idInstalador)
}