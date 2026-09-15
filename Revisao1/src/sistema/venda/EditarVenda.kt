package sistema.venda

import financeiro.Venda
import repository.CRUDCaixaDaAgua
import repository.CRUDCliente
import repository.CRUDInstalador
import repository.CRUDVenda
import sistema.util.lerData
import sistema.util.lerDecimal
import sistema.util.lerInteiro
import sistema.util.lerTexto

fun editarVenda() {
    val conexao = CRUDVenda()
    conexao.listar()

    val id = lerInteiro("Digite o id da venda que deseja editar: ")

    if (!conexao.existePorId(id)) {
        println("Venda com ID $id não foi encontrada!")
        return
    }

    println("Caixas d'água cadastradas:")
    CRUDCaixaDaAgua().listar()
    val idCaixaDaAgua = lerInteiro("Digite o NOVO ID da caixa d'água: ")

    println("Clientes cadastrados:")
    CRUDCliente().listar()
    val idCliente = lerInteiro("Digite o NOVO ID do cliente: ")

    println("Instaladores cadastrados:")
    CRUDInstalador().listar()
    val idInstalador = lerInteiro("Digite o NOVO ID do instalador: ")

    val quantidade = lerInteiro("Digite a NOVA quantidade: ")
    val valor = lerDecimal("Digite o NOVO valor da venda: ")
    val dataVenda = lerData("Digite a NOVA data da venda (AAAA-MM-DD): ")

    val responsavel = lerTexto("Digite o NOVO responsável: ")

    conexao.editar(
        Venda(
            idCaixaDaAgua = idCaixaDaAgua,
            idCliente = idCliente,
            idInstalador = idInstalador,
            quantidade = quantidade,
            valor = valor,
            dataVenda = dataVenda,
            responsavel = responsavel
        ), id
    )
}
