package sistema.instalador

import enumeradores.Habilidade
import enumeradores.Setor
import enumeradores.Turno
import pessoas.Instalador
import repository.CRUDInstalador
import sistema.util.lerCpfValido
import sistema.util.lerDecimal
import sistema.util.lerInteiro
import sistema.util.lerOpcaoEnum
import sistema.util.lerTexto

fun editarInstalador() {
    val conexao = CRUDInstalador()
    conexao.listar()

    val id = lerInteiro("Digite o id do instalador que deseja editar: ")

    // Valida se o ID realmente existe antes de pedir os outros dados
    if (!conexao.existePorId(id)) {
        println("Instalador com ID $id não foi encontrado!")
        return
    }

    val nome = lerTexto("Digite o novo nome: ")
    val cpf = lerCpfValido("Digite o novo CPF (000.000.000-00 ou 11 números): ")
    val idade = lerInteiro("Digite a nova idade: ")
    val salario = lerDecimal("Digite o novo salário: ")

    val turno = lerOpcaoEnum("Escolha o novo turno: ", Turno.entries.toTypedArray())
    val habilidade = lerOpcaoEnum("Escolha a nova habilidade: ", Habilidade.entries.toTypedArray())
    val setor = lerOpcaoEnum("Escolha o novo setor: ", Setor.entries.toTypedArray())

    conexao.editar(
        Instalador(
            nome = nome,
            cpf = cpf,
            idade = idade,
            salario = salario,
            turno = turno,
            habilidade = habilidade,
            setor = setor
        ), id
    )
}