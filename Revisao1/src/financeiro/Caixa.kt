package financeiro

import java.math.BigDecimal

class Caixa(
    val dinheiro: BigDecimal//nao posso mexer via codigo, somente no banco
){
    fun receita(valor: BigDecimal) : BigDecimal{
        return valor
    }
    fun despesa(valor: BigDecimal) : BigDecimal{
        return valor.multiply("-1".toBigDecimal())
    }
}