package sistema

import sistema.caixadaagua.cadastrarNovaCaixa
import sistema.caixadaagua.editarCaixa
import sistema.caixadaagua.listarCaixa
import sistema.caixadaagua.excluirCaixa
import sistema.cliente.cadastrarNovoCliente
import sistema.cliente.editarCliente
import sistema.cliente.listarCliente
import sistema.cliente.excluirCliente
import sistema.instalador.cadastrarNovoInstalador
import sistema.instalador.editarInstalador
import sistema.instalador.listarInstalador
import sistema.instalador.excluirInstalador
import sistema.servico.cadastrarNovoServico
import sistema.servico.editarServico
import sistema.servico.listarServico
import sistema.servico.excluirServico
import sistema.funcionario.cadastrarNovoFuncionario
import sistema.funcionario.editarFuncionario
import sistema.funcionario.listarFuncionario
import sistema.funcionario.excluirFuncionario
import sistema.fornecedor.cadastrarNovoFornecedor
import sistema.fornecedor.editarFornecedor
import sistema.fornecedor.listarFornecedor
import sistema.fornecedor.excluirFornecedor

fun menuInicial(){

    do{
        println("0 - Sair")
        println("------------------------------------")
        println("CAIXA DE AGUA")
        println("1 - Cadastrar Caixa De Água")
        println("2 - Editar Caixa De Água")
        println("3 - Listar Caixa De Água")
        println("4 - Excluir Caixa De Água")
        println("------------------------------------")
        println("CLIENTE")
        println("5 - Cadastrar Cliente")
        println("6 - Editar Cliente")
        println("7 - Listar Cliente")
        println("8 - Excluir Cliente")
        println("------------------------------------")
        println("INSTALADOR")
        println("9 - Cadastrar Instalador")
        println("10 - Editar Instalador")
        println("11 - Listar Instalador")
        println("12 - Excluir Instalador")
        println("------------------------------------")
        println("SERVIÇO")
        println("13 - Cadastrar Serviço")
        println("14 - Editar Serviço")
        println("15 - Listar Serviço")
        println("16 - Excluir Serviço")
        println("------------------------------------")
        println("FUNCIONARIO")
        println("17 - Cadastrar Funcionário")
        println("18 - Editar Funcionário")
        println("19 - Listar Funcionário")
        println("20 - Excluir Funcionário")
        println("------------------------------------")
        println("FORNECEDOR")
        println("21 - Cadastrar Fornecedor")
        println("22 - Editar Fornecedor")
        println("23 - Listar Fornecedor")
        println("24 - Excluir Fornecedor")
        println("------------------------------------")

        val op = readln()
        when(op){
            "1"-> cadastrarNovaCaixa()
            "2"-> editarCaixa()
            "3"-> listarCaixa()
            "4"-> excluirCaixa()
            "5"-> cadastrarNovoCliente()
            "6"-> editarCliente()
            "7"-> listarCliente()
            "8"-> excluirCliente()
            "9"-> cadastrarNovoInstalador()
            "10"-> editarInstalador()
            "11"-> listarInstalador()
            "12"-> excluirInstalador()
            "13"-> cadastrarNovoServico()
            "14"-> editarServico()
            "15"-> listarServico()
            "16"-> excluirServico()
            "17"-> cadastrarNovoFuncionario()
            "18"-> editarFuncionario()
            "19"-> listarFuncionario()
            "20"-> excluirFuncionario()
            "21"-> cadastrarNovoFornecedor()
            "22"-> editarFornecedor()
            "23"-> listarFornecedor()
            "24"-> excluirFornecedor()
            "0"-> {
                println("Tchau Paraguaio")
                break
            }
            else-> println("Opção inválida")
        }
    }while(true) //FIM DO DO-WHILE
} //FIM DA FUNÇAO