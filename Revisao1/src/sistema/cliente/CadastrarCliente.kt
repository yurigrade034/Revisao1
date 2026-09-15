package sistema.cliente

import pessoas.Cliente
import repository.CRUDCliente
import sistema.util.lerCpfValido
import sistema.util.lerDecimal
import sistema.util.lerInteiro
import sistema.util.lerTexto
import java.math.BigDecimal

fun cadastrarCliente() {
    val nome = lerTexto("Nome do cliente: ")
    val cpf = lerCpfValido("CPF do cliente (000.000.000-00 ou 11 números): ")
    val idade = lerInteiro("Idade do cliente: ")

    println("dividas do cliente: " + "1 - Possui" + "2 - Não Possui")
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

    val conexao = CRUDCliente()
    conexao.salvar(
        Cliente(
            nomeCliente = nome,
            cpfCliente = cpf,
            idadeCliente = idade,
            dividasAbertas = dividaAtiva,
            parcelasAPagar = parcelas
        )
    )
}
