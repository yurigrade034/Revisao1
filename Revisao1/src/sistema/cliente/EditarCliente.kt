package sistema.cliente

import pessoas.Cliente
import repository.CRUDCliente
import sistema.util.lerCpfValido
import sistema.util.lerDecimal
import sistema.util.lerInteiro
import sistema.util.lerTexto
import java.math.BigDecimal

fun editarCliente() {
    val conexao = CRUDCliente()
    conexao.listar()

    val id = lerInteiro("Digite o id do cliente que quer editar: ")

    if (!conexao.existePorId(id)) {
        println("Cliente com ID $id não foi encontrado!")
        return
    }

    val nome = lerTexto("Digite o novo nome: ")
    val cpf = lerCpfValido("Digite o novo CPF (000.000.000-00 ou 11 números): ")
    val idade = lerInteiro("Digite a nova idade: ")

    println("dividas ativas do cliente: " + " 1 - Possui" + " 2 - Não Possui")
    var divida = lerInteiro("Digite 1 ou 2: ")
    while (divida != 1 && divida != 2) {
        divida = lerInteiro("Informe um valor entre 1 ou 2: ")
    }
    val dividaAtiva = divida == 1

    val quantidadeParcelas = lerInteiro("Quantas parcelas possui? ")

    val parcelas = mutableListOf<BigDecimal>()
    for (i in 1..quantidadeParcelas) {
        val valor = lerDecimal("Informe o valor da parcela $i: ")
        parcelas.add(valor)
    }

    conexao.editar(
        Cliente(
            nomeCliente = nome,
            cpfCliente = cpf,
            idadeCliente = idade,
            dividasAbertas = dividaAtiva,
            parcelasAPagar = parcelas
        ), id
    )
}