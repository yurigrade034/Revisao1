package sistema

import produto.CaixaDaAgua
import sistema.caixadaagua.cadastrarNovaCaixa
import sistema.caixadaagua.listarCaixa

//deve rodar eternamente, caso raros de reinciar
fun menuInicial(){
    var listaDeTeste : MutableList<CaixaDaAgua> = mutableListOf()
    do{
        println("0 - Sair")
        println("1 - Cadastrar Caixa De Água")
        println("2 - Editar Caixa De Água")
        println("3 - Listar Caixa De Água")
        println("4 - Excluir Caixa De Água")

        val op = readln()
        when(op){
            "1"-> cadastrarNovaCaixa(listaDeTeste)
            "2"-> println()
            "3"-> listarCaixa(listaDeTeste)
            "4"-> println()
            "0"-> {
                println("Tchau Paraguaio")
                break
            }
            else-> println("Opção inválida")
        }
    }while(true) //FIM DO DO-WHILE
} //FIM DA FUNÇAO