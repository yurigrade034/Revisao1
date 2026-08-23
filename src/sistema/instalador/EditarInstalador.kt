package sistema.instalador

import enumeradores.Habilidade
import enumeradores.Turno
import pessoas.Instalador
import repositorio.JPA

fun editarInstalador() {
    val jpa = JPA()
    jpa.listarInstalador()

    println("Digite o ID do instalador que deseja editar")
    val id = readln().toInt()

    println("Digite o novo nome: ")
    val nome = readln()

    println("Digite o novo CPF: ")
    val cpf = readln()

    println("Digite a nova idade: ")
    val idade = readln().toInt()

    println("Digite o novo salário: ")
    val salario = readln().toBigDecimal()

    println("Escolha o novo turno: ")
    Turno.entries.forEach { turno ->
        println("${turno.ordinal} - ${turno.name}")
    }
    println("Número do turno NOVO: ")
    val turno = readln().toInt()

    println("Escolha a nova habilidade: ")
    Habilidade.entries.forEach { habilidade ->
        println("${habilidade.ordinal} - ${habilidade.name}")
    }
    println("Número da habilidade NOVA: ")
    val habilidade = readln().toInt()

    jpa.editarInstalador(
        Instalador(
            nome = nome,
            cpf = cpf,
            idade = idade,
            salario = salario,
            turno = Turno.entries[turno],
            habilidade = Habilidade.entries[habilidade]
        ), id
    )
}