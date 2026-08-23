package sistema.cliente

import pessoas.Cliente
import repositorio.JPA
import java.math.BigDecimal

fun cadastrarNovoCliente() {

    println("Digite o nome: ")
    val nome = readln()

    println("Digite o CPF: ")
    val cpf = readln()

    println("Digite a idade: ")
    val idade = readln().toInt()

    println("O cliente possui dívidas em aberto? (true/false): ")
    val dividasAbertas = readln().toBoolean()

    println("Quantas parcelas em aberto o cliente tem? ")
    val quantidadeParcelas = readln().toInt()
    val parcelasAPagar = mutableListOf<BigDecimal>()
    for (i in 1..quantidadeParcelas) {
        println("Digite o valor da parcela $i: ")
        parcelasAPagar.add(readln().toBigDecimal())
    }

    val conexao = JPA()
    conexao.salvarCliente(
        Cliente(
            nomeCliente = nome,
            cpfCliente = cpf,
            idadeCliente = idade,
            dividasAbertas = dividasAbertas,
            parcelasAPagar = parcelasAPagar
        )
    )
}