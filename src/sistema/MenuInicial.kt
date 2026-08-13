package sistema

import sistema.caixadaagua.cadastrarNovaCaixa

//deve rodar eternamente, caso raros de reinciar
fun menuInicial(){
    do{
        println("0 - Sair")
        println("1 - Cadastrar Caixa De Água")
        println("2 - Editar Caixa De Água")
        println("4 - Listar Caixa De Água")
        println("5 - Excluir Caixa De Água")

        val op = readln()
        when(op){
            "1"-> cadastrarNovaCaixa()
            "2"-> println()
            "3"-> println()
            "4"-> println()
            "0"-> {
                println("Tchau Paraguaio")
                break
            }
            else-> println("Opção inválida")
        }
    }while(true) //FIM DO DO-WHILE
} //FIM DA FUNÇAO