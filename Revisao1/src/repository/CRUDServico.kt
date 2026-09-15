package repository

import produto.Servico
import java.sql.Date
import java.sql.SQLException

class CRUDServico : InterfaceJPA<Servico>, ConexaoPostgres() {

    override fun salvar(item: Servico) {
        try {
            conectar()
            val sql = "INSERT INTO servico (id_cliente, id_instalador, preco, data_instalacao) VALUES (?, ?, ?, ?)"
            val stmt = c!!.prepareStatement(sql)

            stmt.setInt(1, item.idCliente)
            stmt.setInt(2, item.idInstalador)
            stmt.setBigDecimal(3, item.preco)
            stmt.setDate(4, Date.valueOf(item.dataInstalacao))

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
            val sql = "SELECT * FROM servico"
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

    override fun editar(item: Servico, id: Int) {
        try {
            conectar()
            val sql = "UPDATE servico SET id_cliente = ?, id_instalador = ?, preco = ?, data_instalacao = ? WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)

            stmt.setInt(1, item.idCliente)
            stmt.setInt(2, item.idInstalador)
            stmt.setBigDecimal(3, item.preco)
            stmt.setDate(4, Date.valueOf(item.dataInstalacao))
            stmt.setInt(5, id)

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
            val sql = "DELETE FROM servico WHERE id = ?"
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
            val sql = "SELECT 1 FROM servico WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            existe = rs.next()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao verificar serviço por ID:")
            e.printStackTrace()
        }
        return existe
    }
}