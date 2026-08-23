package sistema.cliente

import pessoas.Cliente
import repositorio.JPA
import java.math.BigDecimal

fun editarCliente() {
    val jpa = JPA()
    jpa.listarCliente()

    println("Digite o ID do cliente que deseja editar")
    val id = readln().toInt()

    println("Digite o novo nome: ")
    val nome = readln()

    println("Digite o novo CPF: ")
    val cpf = readln()

    println("Digite a nova idade: ")
    val idade = readln().toInt()

    println("O cliente possui dívidas em aberto? (true/false): ")
    val dividasAbertas = readln().toBoolean()

    println("Quantas parcelas em aberto o cliente tem agora? ")
    val quantidadeParcelas = readln().toInt()
    val parcelasAPagar = mutableListOf<BigDecimal>()
    for (i in 1..quantidadeParcelas) {
        println("Digite o valor da parcela $i: ")
        parcelasAPagar.add(readln().toBigDecimal())
    }

    jpa.editarCliente(
        Cliente(
            nomeCliente = nome,
            cpfCliente = cpf,
            idadeCliente = idade,
            dividasAbertas = dividasAbertas,
            parcelasAPagar = parcelasAPagar
        ), id
    )
}