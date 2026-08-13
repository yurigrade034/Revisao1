package repositorio

import java.sql.Connection
import java.sql.DriverManager
import java.sql.DriverManager.drivers
import java.sql.SQLException

class JPA(
    //porta: 5432
    //user: postgres
    //senha: postgres
    //banco: caixaDaAgua
    val user: String = "postgres",
    val senha: String = "postgres",
    val url: String = "jdbc:postgresql://localhost:5432/caixaDaAgua",
    var conexao: Connection? = null
) {
    fun conectar() {
        try {
            //carregar o driver
            Class.forName("org.postgresql.Driver")

            //estabelecer conexao
            conexao = DriverManager.getConnection(url, user, senha)
            println("A conexão foi estabelecida ")


        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }

}