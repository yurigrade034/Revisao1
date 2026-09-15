package pessoas

import enumeradores.Habilidade
import enumeradores.Setor
import enumeradores.Turno
import java.math.BigDecimal

class Instalador (
    nome : String,
    cpf : String,
    idade : Int,
    val salario : BigDecimal,
    val turno : Turno,
    val habilidade : Habilidade,
    val setor : Setor //requisito do enunciado: funcionário precisa pertencer a um setor
) : Pessoa(
    nome, cpf, idade
)
