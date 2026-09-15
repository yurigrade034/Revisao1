package sistema.servico

import produto.Servico
import repository.CRUDCliente
import repository.CRUDInstalador
import repository.CRUDServico
import sistema.util.lerData
import sistema.util.lerDecimal
import sistema.util.lerInteiro

fun cadastrarServico() {
    println("Clientes cadastrados:")
    CRUDCliente().listar()
    val idCliente = lerInteiro("Digite o ID do cliente: ")

    println("Instaladores cadastrados:")
    CRUDInstalador().listar()
    val idInstalador = lerInteiro("Digite o ID do instalador: ")

    val preco = lerDecimal("Digite o preço do serviço: ")
    val dataInstalacao = lerData("Digite a data de instalação (AAAA-MM-DD): ")

    val conexao = CRUDServico()
    conexao.salvar(
        Servico(
            idCliente = idCliente,
            idInstalador = idInstalador,
            preco = preco,
            dataInstalacao = dataInstalacao
        )
    )
}
