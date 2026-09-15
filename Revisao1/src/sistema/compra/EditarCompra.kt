package sistema.compra

import financeiro.Compra
import repository.CRUDCaixaDaAgua
import repository.CRUDCompra
import repository.CRUDFornecedor
import sistema.util.lerData
import sistema.util.lerDecimal
import sistema.util.lerInteiro
import sistema.util.lerTexto

fun editarCompra() {
    val conexao = CRUDCompra()
    conexao.listar()

    val id = lerInteiro("Digite o id da compra que deseja editar: ")

    if (!conexao.existePorId(id)) {
        println("Compra com ID $id não foi encontrada!")
        return
    }

    println("Caixas d'água cadastradas:")
    CRUDCaixaDaAgua().listar()
    val idCaixaDaAgua = lerInteiro("Digite o NOVO ID da caixa d'água: ")

    val quantidade = lerInteiro("Digite a NOVA quantidade: ")
    val valor = lerDecimal("Digite o NOVO valor da compra: ")
    val dataCompra = lerData("Digite a NOVA data da compra (AAAA-MM-DD): ")

    println("Fornecedores cadastrados:")
    CRUDFornecedor().listar()
    val idFornecedor = lerInteiro("Digite o NOVO ID do fornecedor: ")

    val responsavel = lerTexto("Digite o NOVO responsável: ")

    conexao.editar(
        Compra(
            idCaixaDaAgua = idCaixaDaAgua,
            quantidade = quantidade,
            valor = valor,
            dataCompra = dataCompra,
            idFornecedor = idFornecedor,
            responsavel = responsavel
        ), id
    )
}