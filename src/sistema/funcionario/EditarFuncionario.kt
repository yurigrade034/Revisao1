package sistema.funcionario

import pessoas.Funcionario
import repositorio.JPA

fun editarFuncionario() {
    val jpa = JPA()
    jpa.listarFuncionario()

    println("Digite o ID do funcionário que deseja editar")
    val id = readln().toInt()

    println("Digite o novo nome: ")
    val nome = readln()

    println("Digite o novo CPF: ")
    val cpf = readln()

    println("Digite a nova idade: ")
    val idade = readln().toInt()

    println("Digite o novo salário: ")
    val salario = readln().toBigDecimal()

    println("Digite o novo cargo: ")
    val cargo = readln()

    jpa.editarFuncionario(
        Funcionario(
            nome = nome,
            cpf = cpf,
            idade = idade,
            salario = salario,
            cargo = cargo
        ), id
    )
}