package pessoas

import java.math.BigDecimal

class Funcionario(
    nome: String,
    cpf: String,
    idade: Int,
    val salario: BigDecimal,
    val cargo: String
) : Pessoa(
    nome, cpf, idade
)