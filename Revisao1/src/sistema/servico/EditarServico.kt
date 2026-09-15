package sistema.servico

import produto.Servico
import repository.CRUDCliente
import repository.CRUDInstalador
import repository.CRUDServico
import sistema.util.lerData
import sistema.util.lerDecimal
import sistema.util.lerInteiro

fun editarServico() {
    val conexao = CRUDServico()
    conexao.listar()

    val id = lerInteiro("Digite o id do serviço que deseja editar: ")

    if (!conexao.existePorId(id)) {
        println("Serviço com ID $id não foi encontrado!")
        return
    }

    println("Clientes cadastrados:")
    CRUDCliente().listar()
    val idCliente = lerInteiro("Digite o NOVO ID do cliente: ")

    println("Instaladores cadastrados:")
    CRUDInstalador().listar()
    val idInstalador = lerInteiro("Digite o NOVO ID do instalador: ")

    val preco = lerDecimal("Digite o NOVO preço do serviço: ")
    val dataInstalacao = lerData("Digite a NOVA data de instalação (AAAA-MM-DD): ")

    conexao.editar(
        Servico(
            idCliente = idCliente,
            idInstalador = idInstalador,
            preco = preco,
            dataInstalacao = dataInstalacao
        ), id
    )
}