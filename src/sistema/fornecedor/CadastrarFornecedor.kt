package sistema.fornecedor

import enumeradores.Material
import pessoas.Fornecedor
import repositorio.JPA

fun cadastrarNovoFornecedor() {

    println("Digite o nome: ")
    val nome = readln()

    println("Digite o CPF: ")
    val cpf = readln()

    println("Digite a idade: ")
    val idade = readln().toInt()

    println("Escolha o material que o fornecedor fornece: ")
    Material.entries.forEach { material ->
        println("${material.ordinal} - ${material.name}")
    }
    println("Número do material: ")
    val material = readln().toInt()

    println("Digite o prazo de entrega (em dias): ")
    val prazoEntregaDias = readln().toInt()

    val conexao = JPA()
    conexao.salvarFornecedor(
        Fornecedor(
            nome = nome,
            cpf = cpf,
            idade = idade,
            material = Material.entries[material],
            prazoEntregaDias = prazoEntregaDias
        )
    )
}