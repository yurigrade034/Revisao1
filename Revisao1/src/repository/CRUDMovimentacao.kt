package repository

import financeiro.Movimentacao
import java.sql.SQLException
import java.sql.Timestamp

class CRUDMovimentacao : InterfaceJPA<Movimentacao>, ConexaoPostgres() {

    override fun salvar(item: Movimentacao) {
        try {
            conectar()
            val sql = "INSERT INTO movimentacao (dinheiro, data_movimentacao, descricao, pagador, recebedor, responsavel) VALUES (?, ?, ?, ?, ?, ?)"
            val stmt = c!!.prepareStatement(sql)

            stmt.setBigDecimal(1, item.valor)
            stmt.setTimestamp(2, Timestamp.valueOf(item.dataMovimentacao))
            stmt.setString(3, item.contexto.name)
            stmt.setString(4, item.pagador)
            stmt.setString(5, item.recebedor)
            stmt.setString(6, item.responsavel)

            stmt.executeUpdate()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun listar() {
        try {
            conectar()
            val stmt = c!!.createStatement()
            val sql = "SELECT * FROM movimentacao"
            val metadados = stmt.executeQuery(sql)
            val resultado = metadados.metaData
            val tamanhoTabela = resultado.columnCount

            while (metadados.next()) {
                for (i in 1..tamanhoTabela) {
                    val nomeColuna = resultado.getColumnName(i)
                    val valorColuna = metadados.getString(i)
                    println("$nomeColuna -> $valorColuna")
                }
                println("--------------------------------------------")
            }

            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun editar(item: Movimentacao, id: Int) {
        try {
            conectar()
            val sql = "UPDATE movimentacao SET dinheiro = ?, data_movimentacao = ?, descricao = ?, pagador = ?, recebedor = ?, responsavel = ? WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)

            stmt.setBigDecimal(1, item.valor)
            stmt.setTimestamp(2, Timestamp.valueOf(item.dataMovimentacao))
            stmt.setString(3, item.contexto.name)
            stmt.setString(4, item.pagador)
            stmt.setString(5, item.recebedor)
            stmt.setString(6, item.responsavel)
            stmt.setInt(7, id)

            stmt.executeUpdate()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun excluir(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM movimentacao WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()

            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun auditar() {
        var saldoCalculado = java.math.BigDecimal.ZERO
        try {
            conectar()
            val stmt = c!!.createStatement()
            val sql = "SELECT descricao, dinheiro FROM movimentacao"
            val rs = stmt.executeQuery(sql)

            println("\n--- Auditando Histórico de Movimentações ---")
            while (rs.next()) {
                val descricao = rs.getString("descricao") ?: ""
                val valor = rs.getBigDecimal("dinheiro") ?: java.math.BigDecimal.ZERO

                if (descricao.uppercase() == "VENDA") {
                    saldoCalculado = saldoCalculado.add(valor)
                    println("[+] VENDA: +R$ $valor | Saldo Auditado: R$ $saldoCalculado")
                } else {
                    saldoCalculado = saldoCalculado.subtract(valor)
                    println("[-] SAÍDA ($descricao): -R$ $valor | Saldo Auditado: R$ $saldoCalculado")
                }
            }
            rs.close()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        val saldoCaixaAtual = CRUDCaixa().buscarSaldoAtual().dinheiro

        println("--------------------------------------------")
        println("Saldo Calculado pela Auditoria: R$ $saldoCalculado")
        println("Saldo Registrado no Caixa:        R$ $saldoCaixaAtual")
        println("--------------------------------------------")

        if (saldoCalculado.compareTo(saldoCaixaAtual) == 0) {
            println("STATUS DA AUDITORIA: [ APROVADO ]")
            println("O histórico de movimentações bate perfeitamente com o caixa!")
        } else {
            println("STATUS DA AUDITORIA: [ REPROVADO / DIVERGÊNCIA ]")
            println("ATENÇÃO: O saldo em caixa difere das movimentações registradas!")
        }
    }
}