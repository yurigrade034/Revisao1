package financeiro

import java.math.BigDecimal
import java.time.LocalDate

class Compra(
    val idCaixaDaAgua: Int,
    val quantidade: Int,
    val valor: BigDecimal,
    val dataCompra: LocalDate,
    val idFornecedor: Int, //agora é FK de verdade pra fornecedor
    val responsavel: String //funcionário que registrou a compra
)
