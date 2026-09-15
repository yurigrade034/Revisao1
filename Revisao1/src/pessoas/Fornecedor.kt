package pessoas

class Fornecedor(
    nome: String,
    cpf: String,
    idade: Int,
    val produtoFornecido: String //o que esse fornecedor vende pra empresa
) : Pessoa(
    nome, cpf, idade
)
