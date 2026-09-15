package financeiro

import enumeradores.TipoMovimentacao
import java.math.BigDecimal
import java.time.LocalDateTime

class Movimentacao(
    val valor: BigDecimal,
    val dataMovimentacao: LocalDateTime, //data E hora, exigido pelo enunciado
    val contexto: TipoMovimentacao, //descrição/motivo agora é um enum, não String solta
    val pagador: String,
    val recebedor: String,
    val responsavel: String //quem responde pela transação
)
