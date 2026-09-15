package repository

import enumeradores.TipoMovimentacao
import financeiro.Compra
import financeiro.Movimentacao
import java.math.BigDecimal
import java.sql.Date
import java.sql.SQLException
import java.time.LocalDateTime

class CRUDCompra : InterfaceJPA<Compra>, ConexaoPostgres() {

    override fun salvar(item: Compra) {
        // Pega o preço unitário cadastrado e calcula o valor total (Preço x Quantidade)
        val precoUnitario = CRUDCaixaDaAgua().buscarPrecoPorId(item.idCaixaDaAgua)
        val valorTotalCalculado = precoUnitario.multiply(BigDecimal(item.quantidade))

        // Validação de saldo antes de realizar a compra
        val caixaCRUD = CRUDCaixa()
        if (!caixaCRUD.temSaldoSuficiente(valorTotalCalculado)) {
            val saldoAtual = caixaCRUD.buscarSaldoAtual().dinheiro
            println("==========================================================")
            println("ERRO: COMPRA CANCELADA POR SALDO INSUFICIENTE EM CAIXA!")
            println("Saldo disponível: R$ $saldoAtual")
            println("Valor da compra:  R$ $valorTotalCalculado")
            println("==========================================================")
            return // Interrompe o método antes de alterar o banco
        }

        try {
            conectar()
            val sql = "INSERT INTO compra " +
                    "(id_caixa_da_agua, quantidade, valor, data_compra, id_fornecedor, responsavel) " +
                    "VALUES (?, ?, ?, ?, ?, ?)"
            val stmt = c!!.prepareStatement(sql)

            stmt.setInt(1, item.idCaixaDaAgua)
            stmt.setInt(2, item.quantidade)
            stmt.setBigDecimal(3, valorTotalCalculado)
            stmt.setDate(4, Date.valueOf(item.dataCompra))
            stmt.setInt(5, item.idFornecedor)
            stmt.setString(6, item.responsavel)

            stmt.executeUpdate()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        // Soma no estoque
        CRUDEstoque().somarQuantidade(item.idCaixaDaAgua, item.quantidade)

        // Gera a movimentação financeira com o valor calculado
        CRUDMovimentacao().salvar(
            Movimentacao(
                valor = valorTotalCalculado,
                dataMovimentacao = LocalDateTime.now(),
                contexto = TipoMovimentacao.COMPRA,
                pagador = "EMPRESA",
                recebedor = "FORNECEDOR #${item.idFornecedor}",
                responsavel = item.responsavel
            )
        )

        // Atualiza o Caixa (Desconta a Compra)
        val saldoAnterior = caixaCRUD.buscarSaldoAtual().dinheiro
        val novoSaldo = saldoAnterior.subtract(valorTotalCalculado)
        caixaCRUD.atualizarSaldo(novoSaldo)

        println("Compra registrada com sucesso!")
        println("Valor Total: R$ $valorTotalCalculado (R$ $precoUnitario x ${item.quantidade})")
        println("Saldo anterior: R$ $saldoAnterior | Novo saldo: R$ $novoSaldo")
    }

    override fun listar() {
        try {
            conectar()
            val stmt = c!!.createStatement()
            val sql = "SELECT * FROM compra"
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

    override fun editar(item: Compra, id: Int) {
        try {
            conectar()
            val sql = "UPDATE compra SET id_caixa_da_agua = ?, quantidade = ?, valor = ?, data_compra = ?, id_fornecedor = ?, responsavel = ? WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)

            stmt.setInt(1, item.idCaixaDaAgua)
            stmt.setInt(2, item.quantidade)
            stmt.setBigDecimal(3, item.valor)
            stmt.setDate(4, Date.valueOf(item.dataCompra))
            stmt.setInt(5, item.idFornecedor)
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
            val sql = "DELETE FROM compra WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()

            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun existePorId(id: Int): Boolean {
        var existe = false
        try {
            conectar()
            val sql = "SELECT 1 FROM compra WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            existe = rs.next()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao verificar compra por ID:")
            e.printStackTrace()
        }
        return existe
    }
}