package repositorio

import financeiro.Caixa
import produto.CaixaDaAgua
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

    fun salvar(a: CaixaDaAgua) {
        println("Salvando...")
        try {
            conectar()//abre a conexao com o banco
            conexao!!.createStatement().executeQuery(
                        "INSERT INTO caixa_da_agua " +
                        "(marca, modelo, dimensao, cor, material, formato, preco) " +
                        "VALUES (${a.marca}, ${a.modelo}, ${a.dimensao}, ${a.cor}, ${a.material}, ${a.formato}, ${a.preco}) "
            )
            conexao!!.close()//encerra a conexao com o banco

        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }

}