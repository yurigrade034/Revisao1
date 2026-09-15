package repository

import financeiro.Caixa
import java.math.BigDecimal
import java.sql.SQLException

class CRUDCaixa : ConexaoPostgres() {

    fun buscarSaldoAtual(): Caixa {
        var saldo = BigDecimal.ZERO
        try {
            conectar()
            val sql = "SELECT dinheiro FROM caixa ORDER BY id DESC LIMIT 1"
            val stmt = c!!.prepareStatement(sql)
            val rs = stmt.executeQuery()

            if (rs.next()) {
                saldo = rs.getBigDecimal("dinheiro") ?: BigDecimal.ZERO
            }

            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return Caixa(dinheiro = saldo)
    }

    fun atualizarSaldo(novoSaldo: BigDecimal) {
        try {
            conectar()
            val sql = "INSERT INTO caixa (dinheiro) VALUES (?)"
            val stmt = c!!.prepareStatement(sql)
            stmt.setBigDecimal(1, novoSaldo)
            stmt.executeUpdate()

            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    // Verifica se existe pelo menos uma movimentação/saldo registrado na caixa
    fun existeRegistro(): Boolean {
        var existe = false
        try {
            conectar()
            val sql = "SELECT 1 FROM caixa LIMIT 1"
            val stmt = c!!.prepareStatement(sql)
            val rs = stmt.executeQuery()
            existe = rs.next()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao verificar registros do caixa:")
            e.printStackTrace()
        }
        return existe
    }

    // Método para validar se há saldo suficiente para realizar compras ou saídas
    fun temSaldoSuficiente(valorNecessario: BigDecimal): Boolean {
        val saldoAtual = buscarSaldoAtual().dinheiro
        return saldoAtual >= valorNecessario
    }
}