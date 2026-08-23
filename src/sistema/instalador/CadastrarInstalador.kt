package sistema.instalador

import enumeradores.Habilidade
import enumeradores.Turno
import pessoas.Instalador
import repositorio.JPA

fun cadastrarNovoInstalador() {

    println("Digite o nome: ")
    val nome = readln()

    println("Digite o CPF: ")
    val cpf = readln()

    println("Digite a idade: ")
    val idade = readln().toInt()

    println("Digite o salário: ")
    val salario = readln().toBigDecimal()

    println("Escolha o turno: ")
    Turno.entries.forEach { turno ->
        println("${turno.ordinal} - ${turno.name}")
    }
    println("Número do turno: ")
    val turno = readln().toInt()

    println("Escolha a habilidade: ")
    Habilidade.entries.forEach { habilidade ->
        println("${habilidade.ordinal} - ${habilidade.name}")
    }
    println("Número da habilidade: ")
    val habilidade = readln().toInt()

    val conexao = JPA()
    conexao.salvarInstalador(
        Instalador(
            nome = nome,
            cpf = cpf,
            idade = idade,
            salario = salario,
            turno = Turno.entries[turno],
            habilidade = Habilidade.entries[habilidade]
        )
    )
}