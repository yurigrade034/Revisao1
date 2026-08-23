package repositorio

import financeiro.Caixa
import produto.CaixaDaAgua
import java.sql.Connection
import java.sql.DriverManager
import java.sql.DriverManager.drivers
import java.sql.SQLException
import pessoas.Cliente
import pessoas.Instalador
import pessoas.Funcionario
import java.time.LocalDate
import pessoas.Fornecedor

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
            stmt.setString(1, a.marca)
            stmt.setString(2, a.modelo)
            stmt.setArray(3, doublePrecision)
            stmt.setString(4, a.cor.name)
            stmt.setString(5, a.material.name)
            stmt.setString(6, a.formato)
            stmt.setString(7, a.preco.toString())
            stmt.executeUpdate()

            stmt.close()//encerra o placeholder
            conexao!!.close()//encerra a conexao com o banco
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo salvar

    fun listar() {
        try {
            conectar()
            val stmt = conexao!!.createStatement()
            val sql = "SELECT * FROM caixa_da_agua"
            //Esses metadados vem em forma de Lista, ResultSet
            val metadados = stmt.executeQuery(sql)
            val resultado = metadados.metaData //Metadados do banco
            val tamanhoTabela = resultado.columnCount //Tamanho da tabela em colunas

            while (metadados.next()) {
                for (i in 1..tamanhoTabela) {
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
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo listar

    fun editar(caixa: CaixaDaAgua, id: Int) {
        try {
            conectar()
            val sql = "UPDATE caixa_da_agua SET preco = ?, marca = ? , modelo = ?, formato = ?, cor = ?, material = ?, dimensao = ? WHERE id = ?"
            //continuar a logica para os outros itens
            val stmt = conexao!!.prepareStatement(sql)
            val doublePrecision = conexao!!.createArrayOf("float8", caixa.dimensao.toTypedArray())

            stmt.setString(1, caixa.preco.toString())
            stmt.setString(2, caixa.marca)
            stmt.setString(3, caixa.modelo)
            stmt.setString(4, caixa.formato)
            stmt.setString(5, caixa.cor.name)
            stmt.setString(6, caixa.material.name)
            stmt.setArray(7, doublePrecision)

            stmt.setInt(8, id)
            stmt.executeUpdate()
            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }

        //formato
        //tamanho
        //cor
        //material
        //marca
        //modelo


    }//fim do metodo editar


    fun excluir(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM caixa_da_agua WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()
            stmt.close()
            conexao!!.close()

        }catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }

    }

    // ---------------- CLIENTE ----------------

    fun salvarCliente(c: Cliente) {
        println("Salvando cliente...")
        try {
            conectar()
            val sql = "INSERT INTO cliente " +
                    "(nome, cpf, idade, dividas_abertas, parcelas_a_pagar) " +
                    "VALUES (?, ?, ?, ?, ?) "

            val stmt = conexao!!.prepareStatement(sql)
            val parcelasArray = conexao!!.createArrayOf("numeric", c.parcelasAPagar.toTypedArray())

            stmt.setString(1, c.nome)
            stmt.setString(2, c.cpf)
            stmt.setInt(3, c.idade)
            stmt.setBoolean(4, c.dividasAbertas)
            stmt.setArray(5, parcelasArray)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo salvarCliente

    fun listarCliente() {
        try {
            conectar()
            val stmt = conexao!!.createStatement()
            val sql = "SELECT * FROM cliente"
            val metadados = stmt.executeQuery(sql)
            val resultado = metadados.metaData
            val tamanhoTabela = resultado.columnCount

            while (metadados.next()) {
                for (i in 1..tamanhoTabela) {
                    val nomeColuna = resultado.getColumnName(i)
                    val valorColuna = metadados.getString(i)
                    print("$nomeColuna -> $valorColuna | ")
                }//fim for
                println()
                println("---------------------------------------------------------------")
            }//fim while

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo listarCliente

    fun editarCliente(cliente: Cliente, id: Int) {
        try {
            conectar()
            val sql = "UPDATE cliente SET nome = ?, cpf = ?, idade = ?, dividas_abertas = ?, parcelas_a_pagar = ? WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)
            val parcelasArray = conexao!!.createArrayOf("numeric", cliente.parcelasAPagar.toTypedArray())

            stmt.setString(1, cliente.nome)
            stmt.setString(2, cliente.cpf)
            stmt.setInt(3, cliente.idade)
            stmt.setBoolean(4, cliente.dividasAbertas)
            stmt.setArray(5, parcelasArray)
            stmt.setInt(6, id)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }//fim do metodo editarCliente

    fun excluirCliente(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM cliente WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()
            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }

    // ---------------- INSTALADOR ----------------

    fun salvarInstalador(inst: Instalador) {
        println("Salvando instalador...")
        try {
            conectar()
            val sql = "INSERT INTO instalador " +
                    "(nome, cpf, idade, salario, turno, habilidade) " +
                    "VALUES (?, ?, ?, ?, ?, ?) "

            val stmt = conexao!!.prepareStatement(sql)

            stmt.setString(1, inst.nome)
            stmt.setString(2, inst.cpf)
            stmt.setInt(3, inst.idade)
            stmt.setString(4, inst.salario.toString())
            stmt.setString(5, inst.turno.name)
            stmt.setString(6, inst.habilidade.name)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo salvarInstalador

    fun listarInstalador() {
        try {
            conectar()
            val stmt = conexao!!.createStatement()
            val sql = "SELECT * FROM instalador"
            val metadados = stmt.executeQuery(sql)
            val resultado = metadados.metaData
            val tamanhoTabela = resultado.columnCount

            while (metadados.next()) {
                for (i in 1..tamanhoTabela) {
                    val nomeColuna = resultado.getColumnName(i)
                    val valorColuna = metadados.getString(i)
                    print("$nomeColuna -> $valorColuna | ")
                }
                println()
                println("---------------------------------------------------------------")
            }

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo listarInstalador

    fun editarInstalador(inst: Instalador, id: Int) {
        try {
            conectar()
            val sql = "UPDATE instalador SET nome = ?, cpf = ?, idade = ?, salario = ?, turno = ?, habilidade = ? WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)

            stmt.setString(1, inst.nome)
            stmt.setString(2, inst.cpf)
            stmt.setInt(3, inst.idade)
            stmt.setString(4, inst.salario.toString())
            stmt.setString(5, inst.turno.name)
            stmt.setString(6, inst.habilidade.name)
            stmt.setInt(7, id)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }//fim do metodo editarInstalador

    fun excluirInstalador(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM instalador WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()
            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }

    // ---------------- SERVICO ----------------
    // Repare que aqui NÃO recebemos um objeto Servico inteiro.
    // O Servico da sua classe carrega um Instalador e um Cliente completos dentro dele,
    // mas no banco a tabela servico só guarda o ID de cada um (FK) + os dados próprios
    // do serviço (preco, data). É a entidade associativa que resolve o problema do slide.

    fun salvarServico(preco: String, data: LocalDate, idCliente: Int, idInstalador: Int) {
        println("Salvando serviço...")
        try {
            conectar()
            val sql = "INSERT INTO servico " +
                    "(preco, data_instalacao, id_cliente, id_instalador) " +
                    "VALUES (?, ?, ?, ?) "

            val stmt = conexao!!.prepareStatement(sql)

            stmt.setString(1, preco)
            stmt.setDate(2, java.sql.Date.valueOf(data))
            stmt.setInt(3, idCliente)
            stmt.setInt(4, idInstalador)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo salvarServico

    fun listarServico() {
        try {
            conectar()
            val stmt = conexao!!.createStatement()
            val sql = "SELECT * FROM servico"
            val metadados = stmt.executeQuery(sql)
            val resultado = metadados.metaData
            val tamanhoTabela = resultado.columnCount

            while (metadados.next()) {
                for (i in 1..tamanhoTabela) {
                    val nomeColuna = resultado.getColumnName(i)
                    val valorColuna = metadados.getString(i)
                    print("$nomeColuna -> $valorColuna | ")
                }
                println()
                println("---------------------------------------------------------------")
            }

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo listarServico

    fun editarServico(preco: String, data: LocalDate, idCliente: Int, idInstalador: Int, id: Int) {
        try {
            conectar()
            val sql = "UPDATE servico SET preco = ?, data_instalacao = ?, id_cliente = ?, id_instalador = ? WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)

            stmt.setString(1, preco)
            stmt.setDate(2, java.sql.Date.valueOf(data))
            stmt.setInt(3, idCliente)
            stmt.setInt(4, idInstalador)
            stmt.setInt(5, id)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }//fim do metodo editarServico

    fun excluirServico(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM servico WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()
            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }

    // ---------------- FUNCIONARIO ----------------

    fun salvarFuncionario(f: Funcionario) {
        println("Salvando funcionário...")
        try {
            conectar()
            val sql = "INSERT INTO funcionario " +
                    "(nome, cpf, idade, salario, cargo) " +
                    "VALUES (?, ?, ?, ?, ?) "

            val stmt = conexao!!.prepareStatement(sql)

            stmt.setString(1, f.nome)
            stmt.setString(2, f.cpf)
            stmt.setInt(3, f.idade)
            stmt.setString(4, f.salario.toString())
            stmt.setString(5, f.cargo)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo salvarFuncionario

    fun listarFuncionario() {
        try {
            conectar()
            val stmt = conexao!!.createStatement()
            val sql = "SELECT * FROM funcionario"
            val metadados = stmt.executeQuery(sql)
            val resultado = metadados.metaData
            val tamanhoTabela = resultado.columnCount

            while (metadados.next()) {
                for (i in 1..tamanhoTabela) {
                    val nomeColuna = resultado.getColumnName(i)
                    val valorColuna = metadados.getString(i)
                    print("$nomeColuna -> $valorColuna | ")
                }
                println()
                println("---------------------------------------------------------------")
            }

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo listarFuncionario

    fun editarFuncionario(f: Funcionario, id: Int) {
        try {
            conectar()
            val sql = "UPDATE funcionario SET nome = ?, cpf = ?, idade = ?, salario = ?, cargo = ? WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)

            stmt.setString(1, f.nome)
            stmt.setString(2, f.cpf)
            stmt.setInt(3, f.idade)
            stmt.setString(4, f.salario.toString())
            stmt.setString(5, f.cargo)
            stmt.setInt(6, id)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }//fim do metodo editarFuncionario

    fun excluirFuncionario(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM funcionario WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()
            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }

    // ---------------- FORNECEDOR ----------------

    fun salvarFornecedor(f: Fornecedor) {
        println("Salvando fornecedor...")
        try {
            conectar()
            val sql = "INSERT INTO fornecedor " +
                    "(nome, cpf, idade, material, prazo_entrega_dias) " +
                    "VALUES (?, ?, ?, ?, ?) "

            val stmt = conexao!!.prepareStatement(sql)

            stmt.setString(1, f.nome)
            stmt.setString(2, f.cpf)
            stmt.setInt(3, f.idade)
            stmt.setString(4, f.material.name)
            stmt.setInt(5, f.prazoEntregaDias)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo salvarFornecedor

    fun listarFornecedor() {
        try {
            conectar()
            val stmt = conexao!!.createStatement()
            val sql = "SELECT * FROM fornecedor"
            val metadados = stmt.executeQuery(sql)
            val resultado = metadados.metaData
            val tamanhoTabela = resultado.columnCount

            while (metadados.next()) {
                for (i in 1..tamanhoTabela) {
                    val nomeColuna = resultado.getColumnName(i)
                    val valorColuna = metadados.getString(i)
                    print("$nomeColuna -> $valorColuna | ")
                }
                println()
                println("---------------------------------------------------------------")
            }

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    } //fim do metodo listarFornecedor

    fun editarFornecedor(f: Fornecedor, id: Int) {
        try {
            conectar()
            val sql = "UPDATE fornecedor SET nome = ?, cpf = ?, idade = ?, material = ?, prazo_entrega_dias = ? WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)

            stmt.setString(1, f.nome)
            stmt.setString(2, f.cpf)
            stmt.setInt(3, f.idade)
            stmt.setString(4, f.material.name)
            stmt.setInt(5, f.prazoEntregaDias)
            stmt.setInt(6, id)
            stmt.executeUpdate()

            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }//fim do metodo editarFornecedor

    fun excluirFornecedor(id: Int) {
        try {
            conectar()
            val sql = "DELETE FROM fornecedor WHERE id = ?"
            val stmt = conexao!!.prepareStatement(sql)
            stmt.setInt(1, id)
            stmt.executeUpdate()
            stmt.close()
            conexao!!.close()
        } catch (e: SQLException) {
            println("F total parceiro: ${e.printStackTrace()}")
        }
    }

}