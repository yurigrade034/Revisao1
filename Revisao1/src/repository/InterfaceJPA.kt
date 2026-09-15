package repository

//T é uma classe generica
//a interface é um contrato, as funcoes sao clausulas,
//nossas classes que herdam essa interface
//precisam implementar as funcoes
interface InterfaceJPA<T> {
    //Item é o meu parametro generico
    fun salvar(item: T)
    fun listar()
    fun editar(item: T, id: Int)
    fun excluir(id: Int)



}//fim interfaceJPA