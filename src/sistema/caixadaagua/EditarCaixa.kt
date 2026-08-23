package sistema.caixadaagua

import enumeradores.Cor
import enumeradores.Material
import produto.CaixaDaAgua
import repositorio.JPA

fun editarCaixa() {
    val jpa = JPA()
    jpa.listar()
    //Aqui é só um exemplo de um item
    println("Digite o ID da caixa de agua que deseja editar")
    val id = readln().toInt()


    println("Digite a nova marca: ")
    val marca = readln()

    println("Digite o NOVO modelo: ")
    val modelo = readln()

    println("Digite o NOVO formato: ")
    val formato = readln()

    println("Digite o NOVA dimensão:")
    println("Digite o NOVA largura: ")
    val largura = readln().toDouble()
    println("Digite o NOVA profundidade: ")
    val profundidade = readln().toDouble()
    println("Digite o NOVA altura: ")
    val altura = readln().toDouble()

    val dimensao = mutableListOf(largura, altura, profundidade)

    println("Escolha a cor: ")
    Cor.entries.forEach { cor ->
        println("${cor.ordinal} - ${cor.name}")
    }
    println("numero da cor NOVA: ")
    val cor = readln().toInt()

    println("Escolha o material: ")
    Cor.entries.forEach { material ->
        println("${material.ordinal} - ${material.name}")
    }
    println("Digite o numero do material NOVO: ")
    val material = readln().toInt()

    println("Digite o novo preço: ")
    val preco = readln().toBigDecimal()

jpa.editar(
    CaixaDaAgua(
        marca = marca,
        modelo = modelo,
        formato = formato,
        dimensao = dimensao,
        preco = preco,
        cor = Cor.entries[cor],
        material = Material.entries[material]
    ) ,id
)
}
