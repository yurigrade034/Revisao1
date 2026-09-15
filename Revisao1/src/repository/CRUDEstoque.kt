package repository

import produto.Estoque
import java.sql.SQLException

class CRUDEstoque : ConexaoPostgres() {

    //Não implementa InterfaceJPA de propósito: estoque não tem cadastro/edição/exclusão
    //manual. Quem controla a quantidade é a Compra (soma) e a Venda (subtrai).

    fun listar() {
        try {
            conectar()
            val stmt = c!!.createStatement()
            val sql = "SELECT * FROM estoque"
            val resultado = stmt.executeQuery(sql)

            while (resultado.next()) {
                val item = Estoque(
                    id = resultado.getInt("id"),
                    descricao = resultado.getString("descricao"),
                    quantidade = resultado.getInt("quantidade"),
                    idCaixaDaAgua = resultado.getInt("id_caixa_da_agua")
                )
                println("ID: ${item.id} | ${item.descricao} | Quantidade: ${item.quantidade} | Caixa d'água: #${item.idCaixaDaAgua}")
                println("--------------------------------------------")
            }

            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            println(e.printStackTrace())
        }
    }

    fun buscarQuantidadePorId(idCaixaDaAgua: Int): Int {
        var qtd = 0
        try {
            conectar()
            val sql = "SELECT quantidade FROM estoque WHERE id_caixa_da_agua = ?"
            val stmt = c!!.prepareStatement(sql)
            stmt.setInt(1, idCaixaDaAgua)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                qtd = rs.getInt("quantidade")
            }
            stmt.close()
            c!!.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return qtd
    }

    fun temEstoqueSuficiente(idCaixaDaAgua: Int, quantidadeDesejada: Int): Boolean {
        val quantidadeAtual = buscarQuantidadePorId(idCaixaDaAgua)
        return quantidadeAtual >= quantidadeDesejada
    }

    //Chamado internamente pela Compra: soma a quantidade comprada.
    //Se ainda não existir estoque para essa caixa d'água, cria a linha.
    fun somarQuantidade(idCaixaDaAgua: Int, quantidade: Int) {
        try {
            conectar()
            val sqlBusca = "SELECT id, quantidade FROM estoque WHERE id_caixa_da_agua = ?"
            val stmtBusca = c!!.prepareStatement(sqlBusca)
            stmtBusca.setInt(1, idCaixaDaAgua)
            val resultado = stmtBusca.executeQuery()

            if (resultado.next()) {
                val idEstoque = resultado.getInt("id")
                val quantidadeAtual = resultado.getInt("quantidade")

                val sqlUpdate = "UPDATE estoque SET quantidade = ? WHERE id = ?"
                val stmtUpdate = c!!.prepareStatement(sqlUpdate)
                stmtUpdate.setInt(1, quantidadeAtual + quantidade)
                stmtUpdate.setInt(2, idEstoque)
                stmtUpdate.executeUpdate()
                stmtUpdate.close()
            } else {
                val sqlInsert = "INSERT INTO estoque (descricao, quantidade, id_caixa_da_agua) VALUES (?, ?, ?)"
                val stmtInsert = c!!.prepareStatement(sqlInsert)
                stmtInsert.setString(1, "Estoque da caixa d'água #$idCaixaDaAgua")
                stmtInsert.setInt(2, quantidade)
                stmtInsert.setInt(3, idCaixaDaAgua)
                stmtInsert.executeUpdate()
                stmtInsert.close()
            }

            stmtBusca.close()
            c!!.close()
        } catch (e: SQLException) {
            println(e.printStackTrace())
        }
    }

    //Chamado internamente pela Venda: desconta a quantidade vendida.
    fun subtrairQuantidade(idCaixaDaAgua: Int, quantidade: Int) {
        try {
            conectar()
            val sqlBusca = "SELECT id, quantidade FROM estoque WHERE id_caixa_da_agua = ?"
            val stmtBusca = c!!.prepareStatement(sqlBusca)
            stmtBusca.setInt(1, idCaixaDaAgua)
            val resultado = stmtBusca.executeQuery()

            if (resultado.next()) {
                val idEstoque = resultado.getInt("id")
                val quantidadeAtual = resultado.getInt("quantidade")

                //Encapsulamento da regra de negócio: o estoque nunca pode ficar negativo.
                //Só o próprio CRUDEstoque decide isso — ninguém de fora consegue forçar
                //uma quantidade negativa direto no banco.
                if (quantidadeAtual - quantidade < 0) {
                    println("Venda cancelada: estoque insuficiente (disponível: $quantidadeAtual, solicitado: $quantidade)")
                } else {
                    val sqlUpdate = "UPDATE estoque SET quantidade = ? WHERE id = ?"
                    val stmtUpdate = c!!.prepareStatement(sqlUpdate)
                    stmtUpdate.setInt(1, quantidadeAtual - quantidade)
                    stmtUpdate.setInt(2, idEstoque)
                    stmtUpdate.executeUpdate()
                    stmtUpdate.close()
                }
            } else {
                println("Não há estoque cadastrado para essa caixa d'água!")
            }

            stmtBusca.close()
            c!!.close()
        } catch (e: SQLException) {
            println(e.printStackTrace())
        }
    }
}