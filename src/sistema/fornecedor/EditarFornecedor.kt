package sistema.fornecedor

import enumeradores.Material
import pessoas.Fornecedor
import repositorio.JPA

fun editarFornecedor() {
    val jpa = JPA()
    jpa.listarFornecedor()

    println("Digite o ID do fornecedor que deseja editar")
    val id = readln().toInt()

    println("Digite o novo nome: ")
    val nome = readln()

    println("Digite o novo CPF: ")
    val cpf = readln()

    println("Digite a nova idade: ")
    val idade = readln().toInt()

    println("Escolha o novo material: ")
    Material.entries.forEach { material ->
        println("${material.ordinal} - ${material.name}")
    }
    println("Número do material NOVO: ")
    val material = readln().toInt()

    println("Digite o novo prazo de entrega (em dias): ")
    val prazoEntregaDias = readln().toInt()

    jpa.editarFornecedor(
        Fornecedor(
            nome = nome,
            cpf = cpf,
            idade = idade,
            material = Material.entries[material],
            prazoEntregaDias = prazoEntregaDias
        ), id
    )
}