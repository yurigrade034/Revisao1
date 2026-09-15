package sistema.venda

import financeiro.Venda
import repository.CRUDCaixa
import repository.CRUDCaixaDaAgua
import repository.CRUDCliente
import repository.CRUDEstoque
import repository.CRUDInstalador
import repository.CRUDVenda
import sistema.util.lerData
import sistema.util.lerInteiro
import sistema.util.lerTexto
import java.math.BigDecimal

fun cadastrarVenda() {
    val saldoAtual = CRUDCaixa().buscarSaldoAtual().dinheiro
    println("==========================================")
    println("           CADASTRO DE VENDA              ")
    println("Saldo Atual em Caixa: R$ $saldoAtual")
    println("==========================================")

    println("Caixas d'água cadastradas:")
    CRUDCaixaDaAgua().listar()
    val idCaixaDaAgua = lerInteiro("Digite o ID da caixa d'água vendida: ")

    val estoqueDisponivel = CRUDEstoque().buscarQuantidadePorId(idCaixaDaAgua)
    println("Estoque disponível para esta caixa: $estoqueDisponivel unidades")

    if (estoqueDisponivel <= 0) {
        println("ERRO: Produto sem estoque disponível para venda!")
        return
    }

    val quantidade = lerInteiro("Digite a quantidade vendida: ")

    if (quantidade > estoqueDisponivel) {
        println("ERRO: Quantidade solicitada ($quantidade) é maior que o estoque disponível ($estoqueDisponivel)!")
        return
    }

    println("Clientes cadastrados:")
    CRUDCliente().listar()
    val idCliente = lerInteiro("Digite o ID do cliente: ")

    println("Instaladores cadastrados:")
    CRUDInstalador().listar()
    val idInstalador = lerInteiro("Digite o ID do instalador que vai instalar: ")

    val dataVenda = lerData("Digite a data da venda (AAAA-MM-DD): ")

    val responsavel = lerTexto("Digite o nome do responsável por essa venda: ")

    val conexao = CRUDVenda()
    conexao.salvar(
        Venda(
            idCaixaDaAgua = idCaixaDaAgua,
            idCliente = idCliente,
            idInstalador = idInstalador,
            quantidade = quantidade,
            valor = BigDecimal.ZERO, // O valor real é calculado automaticamente no CRUDVenda
            dataVenda = dataVenda,
            responsavel = responsavel
        )
    )
}
