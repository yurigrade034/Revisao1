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

fun cadastrarInstalador() {
    val nome = lerTexto("Nome do instalador: ")
    val cpf = lerCpfValido("CPF do instalador (000.000.000-00 ou 11 números): ")
    val idade = lerInteiro("Idade do instalador: ")
    val salario = lerDecimal("Salário do instalador: ")

    val turno = lerOpcaoEnum("Escolha o turno: ", Turno.entries.toTypedArray())
    val habilidade = lerOpcaoEnum("Escolha a habilidade: ", Habilidade.entries.toTypedArray())
    val setor = lerOpcaoEnum("Escolha o setor: ", Setor.entries.toTypedArray())

    val conexao = CRUDInstalador()
    conexao.salvar(
        Instalador(
            nome = nome,
            cpf = cpf,
            idade = idade,
            salario = salario,
            turno = turno,
            habilidade = habilidade,
            setor = setor
        )
    )
}
