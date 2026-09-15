package sistema.fornecedor

import pessoas.Fornecedor
import repository.CRUDFornecedor
import sistema.util.lerCpfValido
import sistema.util.lerInteiro
import sistema.util.lerTexto

fun editarFornecedor() {
    val conexao = CRUDFornecedor()
    conexao.listar()

    val id = lerInteiro("Digite o id do fornecedor que deseja editar: ")

    if (!conexao.existePorId(id)) {
        println("Fornecedor com ID $id não foi encontrado!")
        return
    }

    val nome = lerTexto("Digite o novo nome: ")
    val cpf = lerCpfValido("Digite o novo CPF/CNPJ (000.000.000-00 ou 11 números): ")
    val idade = lerInteiro("Digite a nova idade/tempo de empresa: ")
    val produtoFornecido = lerTexto("Digite o novo produto fornecido: ")

    conexao.editar(
        Fornecedor(
            nome = nome,
            cpf = cpf,
            idade = idade,
            produtoFornecido = produtoFornecido
        ), id
    )
}