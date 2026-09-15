package sistema.compra

import financeiro.Compra
import repository.CRUDCaixa
import repository.CRUDCaixaDaAgua
import repository.CRUDCompra
import repository.CRUDFornecedor
import sistema.util.lerData
import sistema.util.lerInteiro
import sistema.util.lerTexto
import java.math.BigDecimal

fun cadastrarCompra() {
    val saldoAtual = CRUDCaixa().buscarSaldoAtual().dinheiro
    println("==========================================")
    println("           CADASTRO DE COMPRA             ")
    println("Saldo disponível em Caixa: R$ $saldoAtual")
    println("==========================================")

    println("Caixas d'água cadastradas:")
    CRUDCaixaDaAgua().listar()
    val idCaixaDaAgua = lerInteiro("Digite o ID da caixa d'água comprada: ")

    val quantidade = lerInteiro("Digite a quantidade comprada: ")
    val dataCompra = lerData("Digite a data da compra (AAAA-MM-DD): ")

    println("Fornecedores cadastrados:")
    CRUDFornecedor().listar()
    val idFornecedor = lerInteiro("Digite o ID do fornecedor: ")

    val responsavel = lerTexto("Digite o nome do responsável por essa compra: ")

    val conexao = CRUDCompra()
    conexao.salvar(
        Compra(
            idCaixaDaAgua = idCaixaDaAgua,
            quantidade = quantidade,
            valor = BigDecimal.ZERO, // O valor real é calculado automaticamente no CRUDCompra
            dataCompra = dataCompra,
            idFornecedor = idFornecedor,
            responsavel = responsavel
        )
    )
}