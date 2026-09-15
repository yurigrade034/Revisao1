package repository

import pessoas.Fornecedor
import java.sql.SQLException

class CRUDFornecedor : InterfaceJPA<Fornecedor>, ConexaoPostgres() {

    override fun salvar(item: Fornecedor) {
        try {
            conectar()
            val sql = "INSERT INTO fornecedor (nome, cpf, idade, produto_fornecido) VALUES (?, ?, ?, ?)"
            val stmt = c!!.prepareStatement(sql)

            stmt.setString(1, item.nome)
            stmt.setString(2, item.cpf)
            stmt.setInt(3, item.idade)
            stmt.setString(4, item.produtoFornecido)

            stmt.executeUpdate()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println(e.printStackTrace())
        }
    }

    override fun listar() {
        try {
            conectar()
            val stmt = c!!.createStatement()
            val sql = "SELECT * FROM fornecedor"
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
            println(e.printStackTrace())
        }
    }

    override fun editar(item: Fornecedor, id: Int) {
        try {
            conectar()
            val sql = "UPDATE fornecedor SET nome = ?, cpf = ?, idade = ?, produto_fornecido = ? WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)

            stmt.setString(1, item.nome)
            stmt.setString(2, item.cpf)
            stmt.setInt(3, item.idade)
            stmt.setString(4, item.produtoFornecido)
            stmt.setInt(5, id)

            stmt.executeUpdate()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println(e.printStackTrace())
        }
    }

    override fun excluir(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM fornecedor WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()

            c!!.close()
        } catch (e: SQLException) {
            println(e.printStackTrace())
        }
    }

    fun existePorId(id: Int): Boolean {
        var existe = false
        try {
            conectar()
            val sql = "SELECT 1 FROM fornecedor WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            existe = rs.next()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao verificar fornecedor por ID:")
            e.printStackTrace()
        }
        return existe
    }
}