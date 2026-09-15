package repository

import pessoas.Instalador
import java.sql.SQLException

class CRUDInstalador : InterfaceJPA<Instalador>, ConexaoPostgres() {

    override fun salvar(item: Instalador) {
        try {
            conectar()
            val sql = "INSERT INTO instalador " +
                    "(nome, cpf, idade, salario, turno, habilidade, setor) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)"
            val stmt = c!!.prepareStatement(sql)

            stmt.setString(1, item.nome)
            stmt.setString(2, item.cpf)
            stmt.setInt(3, item.idade)
            stmt.setBigDecimal(4, item.salario)
            stmt.setString(5, item.turno.name)
            stmt.setString(6, item.habilidade.name)
            stmt.setString(7, item.setor.name)

            stmt.executeUpdate()

            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao salvar instalador:")
            e.printStackTrace()
        }
    }

    override fun listar() {
        try {
            conectar()
            val stmt = c!!.createStatement()
            val sql = "SELECT * FROM instalador"
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
            println("Erro ao listar instaladores:")
            e.printStackTrace()
        }
    }

    override fun editar(item: Instalador, id: Int) {
        try {
            conectar()
            val sql =
                "UPDATE instalador SET nome = ?, cpf = ?, idade = ?, salario = ?, turno = ?, habilidade = ?, setor = ? WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)

            stmt.setString(1, item.nome)
            stmt.setString(2, item.cpf)
            stmt.setInt(3, item.idade)
            stmt.setBigDecimal(4, item.salario)
            stmt.setString(5, item.turno.name)
            stmt.setString(6, item.habilidade.name)
            stmt.setString(7, item.setor.name)

            stmt.setInt(8, id)

            stmt.executeUpdate()
            stmt.close()
            c!!.close()

        } catch (e: SQLException) {
            println("Erro ao editar instalador:")
            e.printStackTrace()
        }
    }

    override fun excluir(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM instalador WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()

            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao excluir instalador:")
            e.printStackTrace()
        }
    }

    // Método para verificar se o ID existe antes de editar ou excluir
    fun existePorId(id: Int): Boolean {
        var existe = false
        try {
            conectar()
            val sql = "SELECT 1 FROM instalador WHERE id = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            existe = rs.next()
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println("Erro ao verificar instalador por ID:")
            e.printStackTrace()
        }
        return existe
    }
}