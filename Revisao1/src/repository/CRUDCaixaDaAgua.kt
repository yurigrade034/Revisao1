package repository

import enumeradores.Cor
import enumeradores.Material
import produto.CaixaDaAgua
import java.math.BigDecimal
import java.sql.SQLException

class CRUDCaixaDaAgua : InterfaceJPA<CaixaDaAgua>, ConexaoPostgres() {

    override fun salvar(item: CaixaDaAgua) {
        try {
            conectar()
            val sql = "INSERT INTO caixa_da_agua (marca, modelo, dimensao, cor, material, formato, preco) VALUES (?, ?, ?, ?, ?, ?, ?)"
            val stmt = c!!.prepareStatement(sql)

            stmt.setString(1, item.marca)
            stmt.setString(2, item.modelo)
            stmt.setArray(3, c!!.createArrayOf("numeric", item.dimensao.toTypedArray()))
            stmt.setString(4, item.cor.name)
            stmt.setString(5, item.material.name)
            stmt.setString(6, item.formato)
            stmt.setBigDecimal(7, item.preco)

            stmt.executeUpdate()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun editar(item: CaixaDaAgua, id: Int) {
        try {
            conectar()
            val sql = "UPDATE caixa_da_agua SET marca = ?, modelo = ?, dimensao = ?, cor = ?, material = ?, formato = ?, preco = ? WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)

            stmt.setString(1, item.marca)
            stmt.setString(2, item.modelo)
            stmt.setArray(3, c!!.createArrayOf("numeric", item.dimensao.toTypedArray()))
            stmt.setString(4, item.cor.name)
            stmt.setString(5, item.material.name)
            stmt.setString(6, item.formato)
            stmt.setBigDecimal(7, item.preco)
            stmt.setInt(8, id)

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
            val sql = "SELECT * FROM caixa_da_agua"
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

    override fun excluir(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM caixa_da_agua WHERE id = ?"
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
            val sql = "SELECT 1 FROM caixa_da_agua WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            existe = rs.next()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao verificar caixa d'água por ID:")
            e.printStackTrace()
        }
        return existe
    }

    fun buscarPrecoPorId(id: Int): BigDecimal {
        var preco = BigDecimal.ZERO
        try {
            conectar()
            val sql = "SELECT preco FROM caixa_da_agua WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                preco = rs.getBigDecimal("preco") ?: BigDecimal.ZERO
            }
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao buscar preço da caixa d'água:")
            e.printStackTrace()
        }
        return preco
    }
}