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
            val sql = "INSERT INTO caixa_da_agua " +
                        "(marca, modelo, dimensao, cor, material, formato, preco) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) "

            val stmt = conexao!!.prepareStatement(sql)

            //preparar lista para double precision
            val doublePrecision = conexao!!.createArrayOf("float8", a.dimensao.toTypedArray())
            //o typedArray() converte o array para um tipo de dado legivel para o POSTGRESQL

            //Preparar as variaveis para o banco
            stmt.setString(1,a.marca)
            stmt.setString(2,a.modelo)
            stmt.setArray(3,doublePrecision)
            stmt.setString(4,a.cor.name)
            stmt.setString(5,a.material.name)
            stmt.setString(6,a.formato)
            stmt.setString(7,a.preco.toString())
            stmt.executeUpdate()

            stmt.close()//encerra o placeholder
            conexao!!.close()//encerra a conexao com o banco
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo salvar

    fun listar (){
        try{
            conectar()
            val stmt = conexao!!.createStatement()
            val sql = "SELECT * FROM caixa_da_agua"
            //Esses metadados vem em forma de Lista, ResultSet
            val metadados = stmt.executeQuery(sql)
            val resultado = metadados.metaData //Metadados do banco
            val tamanhoTabela = resultado.columnCount //Tamanho da tabela em colunas

            while(metadados.next()){
                for(i in 1..tamanhoTabela){
                    //nome da coluna
                    val nomeColuna = resultado.getColumnName(i)
                    //dado que esta nessa coluna
                    val valorColuna = metadados.getString(i)
                    println("$nomeColuna -> $valorColuna")
                }//fim for
                println("---------------------------------------------------------------")

            }//fim while

            stmt.close()
            conexao!!.close()
        }catch (e: SQLException){
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo listar

}