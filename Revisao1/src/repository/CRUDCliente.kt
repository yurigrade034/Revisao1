package repository

import pessoas.Cliente
import java.sql.SQLException

class CRUDCliente : InterfaceJPA<Cliente>, ConexaoPostgres() {

    override fun salvar(item: Cliente) {
        try {
            conectar()
            val sql = "INSERT INTO cliente" +
                    "(nome, cpf, idade, \"dividasAbertas\", \"parcelasAPagar\")" +
                    "VALUES (?, ?, ?, ?, ?)"
            val stmt = c!!.prepareStatement(sql)

            val parcelapagar = c!!.createArrayOf("numeric", item.parcelasAPagar.toTypedArray())

            stmt.setString(1, item.nome)
            stmt.setString(2, item.cpf)
            stmt.setInt(3, item.idade)
            stmt.setBoolean(4, item.dividasAbertas)
            stmt.setArray(5, parcelapagar)

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
            val sql = "SELECT * FROM cliente"
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
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }

    override fun editar(item: Cliente, id: Int) {
        try {
            conectar()
            val sql =
                "UPDATE cliente SET nome = ?, cpf = ?, idade = ?, \"dividasAbertas\" = ?, \"parcelasAPagar\" = ? WHERE id = ?"
            val parcelapagar = c!!.createArrayOf("numeric", item.parcelasAPagar.toTypedArray())

            val stmt = c!!.prepareStatement(sql)

            stmt.setString(1, item.nome)
            stmt.setString(2, item.cpf)
            stmt.setInt(3, item.idade)
            stmt.setBoolean(4, item.dividasAbertas)
            stmt.setArray(5, parcelapagar)

            stmt.setInt(6, id)

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
            val sql = "DELETE FROM cliente WHERE id = ?"
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
            val sql = "SELECT 1 FROM cliente WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            existe = rs.next()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao verificar cliente por ID:")
            e.printStackTrace()
        }
        return existe
    }
}