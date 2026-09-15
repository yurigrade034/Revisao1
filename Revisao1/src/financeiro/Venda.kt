package financeiro

import java.math.BigDecimal
import java.time.LocalDate

class Venda(
    val idCaixaDaAgua: Int,
    val quantidade: Int,
    val valor: BigDecimal,
    val dataVenda: LocalDate,
    val idCliente: Int,
    val idInstalador: Int,
    val responsavel: String = "SISTEMA"
)