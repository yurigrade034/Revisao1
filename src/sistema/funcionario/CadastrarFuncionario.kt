package sistema.funcionario

import pessoas.Funcionario
import repositorio.JPA

fun cadastrarNovoFuncionario() {

    println("Digite o nome: ")
    val nome = readln()

    println("Digite o CPF: ")
    val cpf = readln()

    println("Digite a idade: ")
    val idade = readln().toInt()

    println("Digite o salário: ")
    val salario = readln().toBigDecimal()

    println("Digite o cargo: ")
    val cargo = readln()

    val conexao = JPA()
    conexao.salvarFuncionario(
        Funcionario(
            nome = nome,
            cpf = cpf,
            idade = idade,
            salario = salario,
            cargo = cargo
        )
    )
}