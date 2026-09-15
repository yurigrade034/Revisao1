package produto

import java.math.BigDecimal
import java.time.LocalDate

class Servico(
    val idCliente: Int,
    val idInstalador: Int,
    val preco: BigDecimal,
    val dataInstalacao: LocalDate
)
