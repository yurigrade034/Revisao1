package sistema.fornecedor

import pessoas.Fornecedor
import repository.CRUDFornecedor
import sistema.util.lerCpfValido
import sistema.util.lerInteiro
import sistema.util.lerTexto

fun cadastrarFornecedor() {
    val nome = lerTexto("Nome do fornecedor: ")
    val cpf = lerCpfValido("CPF/CNPJ do fornecedor (000.000.000-00 ou 11 números): ")
    val idade = lerInteiro("Idade/tempo de empresa (anos): ")
    val produtoFornecido = lerTexto("O que esse fornecedor fornece: ")

    val conexao = CRUDFornecedor()
    conexao.salvar(
        Fornecedor(
            nome = nome,
            cpf = cpf,
            idade = idade,
            produtoFornecido = produtoFornecido
        )
    )
}
