package sistema

import sistema.caixadaagua.*
import sistema.cliente.*
import sistema.instalador.*
import sistema.fornecedor.*
import sistema.servico.*
import sistema.compra.*
import sistema.venda.*
import sistema.estoque.*
import sistema.pagamentos.*

fun menuInicial() {
    do {
        println("\n==========================================================================================")
        println("                           SISTEMA DE GESTÃO - LOJA DE CAIXAS D'ÁGUA                      ")
        println("==========================================================================================")
        println("  [ CAIXA D'ÁGUA ]          [ CLIENTES ]            [ INSTALADORES ]       [ FORNECEDORES ]")
        println("  1. Cadastrar              5. Cadastrar            9. Cadastrar           13. Cadastrar")
        println("  2. Editar                 6. Editar               10. Editar             14. Editar")
        println("  3. Listar                 7. Listar               11. Listar             15. Listar")
        println("  4. Excluir                8. Excluir              12. Excluir            16. Excluir")
        println("──────────────────────────────────────────────────────────────────────────────────────────")
        println("  [ SERVIÇOS ]              [ COMPRAS ]             [ VENDAS ]             [ ESTOQUE / SALDO ]")
        println("  17. Cadastrar             21. Cadastrar           25. Cadastrar          29. Listar Estoque")
        println("  18. Editar                22. Editar              26. Editar")
        println("  19. Listar                23. Listar              27. Listar")
        println("  20. Excluir               24. Excluir             28. Excluir")
        println("──────────────────────────────────────────────────────────────────────────────────────────")
        println("  [ FINANCEIRO ]")
        println("  30. Pagar Salário Instalador")
        println("  31. Listar Movimentações")
        println("  32. Excluir Movimentação")
        println("  33. Auditar Caixa (confere histórico x saldo)")
        println("==========================================================================================")
        println("  0. SAIR DO SISTEMA")
        println("==========================================================================================")
        print("Digite a opção desejada: ")

        val op: Int = readln().toIntOrNull() ?: -1
        println() // Quebra de linha visual após a escolha

        when (op) {
            1 -> cadastrarNovaCaixa()
            2 -> editarCaixa()
            3 -> listarCaixa()
            4 -> excluirCaixa()
            5 -> cadastrarCliente()
            6 -> editarCliente()
            7 -> listarCliente()
            8 -> excluirCliente()
            9 -> cadastrarInstalador()
            10 -> editarInstalador()
            11 -> listarInstalador()
            12 -> excluirInstalador()
            13 -> cadastrarFornecedor()
            14 -> editarFornecedor()
            15 -> listarFornecedor()
            16 -> excluirFornecedor()
            17 -> cadastrarServico()
            18 -> editarServico()
            19 -> listarServico()
            20 -> excluirServico()
            21 -> cadastrarCompra()
            22 -> editarCompra()
            23 -> listarCompra()
            24 -> excluirCompra()
            25 -> cadastrarVenda()
            26 -> editarVenda()
            27 -> listarVenda()
            28 -> excluirVenda()
            29 -> listarEstoque()
            30 -> pagarSalarioInstalador()
            31 -> listarPagamento()
            32 -> excluirPagamento()
            33 -> auditarCaixa()
            0 -> {
                println("Encerrando o sistema. Até logo!")
                break
            }
            else -> println("Opção inválida! Tente novamente.")
        }
    } while (true)
}
