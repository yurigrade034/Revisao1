package pessoas

import enumeradores.Material

class Fornecedor(
    nome: String,
    cpf: String,
    idade: Int,
    val material: Material,
    val prazoEntregaDias: Int
) : Pessoa(
    nome, cpf, idade
)