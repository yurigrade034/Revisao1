package repository

import enumeradores.TipoMovimentacao
import financeiro.Movimentacao
import financeiro.Venda
import java.math.BigDecimal
import java.sql.Date
import java.sql.SQLException
import java.time.LocalDateTime

class CRUDVenda : InterfaceJPA<Venda>, ConexaoPostgres() {

    override fun salvar(item: Venda) {
        val estoqueDisponivel = CRUDEstoque().buscarQuantidadePorId(item.idCaixaDaAgua)
        if (item.quantidade > estoqueDisponivel) {
            println("Operação cancelada: Estoque insuficiente.")
            return
        }

        // Pega o preço unitário cadastrado e calcula o valor total (Preço x Quantidade)
        val precoUnitario = CRUDCaixaDaAgua().buscarPrecoPorId(item.idCaixaDaAgua)
        val valorTotalCalculado = precoUnitario.multiply(BigDecimal(item.quantidade))

        try {
            conectar()
            val sql = "INSERT INTO venda (id_caixa_da_agua, quantidade, valor, data_venda, id_cliente, id_instalador, responsavel) VALUES (?, ?, ?, ?, ?, ?, ?)"
            val stmt = c!!.prepareStatement(sql)

            stmt.setInt(1, item.idCaixaDaAgua)
            stmt.setInt(2, item.quantidade)
            stmt.setBigDecimal(3, valorTotalCalculado)
            stmt.setDate(4, Date.valueOf(item.dataVenda))
            stmt.setInt(5, item.idCliente)
            stmt.setInt(6, item.idInstalador)
            stmt.setString(7, item.responsavel)

            stmt.executeUpdate()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
            return
        }

        CRUDEstoque().subtrairQuantidade(item.idCaixaDaAgua, item.quantidade)

        CRUDMovimentacao().salvar(
            Movimentacao(
                valor = valorTotalCalculado,
                dataMovimentacao = LocalDateTime.now(),
                contexto = TipoMovimentacao.VENDA,
                pagador = "CLIENTE #${item.idCliente}",
                recebedor = "EMPRESA",
                responsavel = item.responsavel
            )
        )

        val caixaCRUD = CRUDCaixa()
        val caixaAtual = caixaCRUD.buscarSaldoAtual()
        val novoSaldo = caixaAtual.dinheiro.add(valorTotalCalculado)
        caixaCRUD.atualizarSaldo(novoSaldo)

        println("Venda registrada com sucesso!")
        println("Valor Total: R$ $valorTotalCalculado (R$ $precoUnitario x ${item.quantidade})")
        println("Novo saldo no caixa: R$ $novoSaldo")
    }

    override fun listar() {
        try {
            conectar()
            val stmt = c!!.createStatement()
            val sql = "SELECT * FROM venda"
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

    override fun editar(item: Venda, id: Int) {
        try {
            conectar()
            val sql = "UPDATE venda SET id_caixa_da_agua = ?, quantidade = ?, valor = ?, data_venda = ?, id_cliente = ?, id_instalador = ?, responsavel = ? WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)

            stmt.setInt(1, item.idCaixaDaAgua)
            stmt.setInt(2, item.quantidade)
            stmt.setBigDecimal(3, item.valor)
            stmt.setDate(4, Date.valueOf(item.dataVenda))
            stmt.setInt(5, item.idCliente)
            stmt.setInt(6, item.idInstalador)
            stmt.setString(7, item.responsavel)
            stmt.setInt(8, id)

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
            val sql = "DELETE FROM venda WHERE id = ?"
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
            val sql = "SELECT 1 FROM venda WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            existe = rs.next()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao verificar venda por ID:")
            e.printStackTrace()
        }
        return existe
    }
}