package sistema.caixadaagua
import enumeradores.Cor
import enumeradores.Material
import produto.CaixaDaAgua
import repositorio.JPA


fun cadastrarNovaCaixa(){

    println("Digite a marca: ")
    val marca = readln()

    println("Digite o modelo: ")
    val modelo = readln()

    println("Digite a largura: ")
    val largura = readln().toDouble()
    println("Digite a altura: ")
    val altura = readln().toDouble()
    println("Digite a profundidade: ")
    val profundidade = readln().toDouble()
    //a dimensao é uma lista dos 3 valores acima
    val dimensao = mutableListOf<Double>(largura, altura, profundidade)

    println("Escolha a cor: ")
    Cor.entries.forEach { cor ->
        println("${cor.ordinal} - ${cor.name}")
    }
    println("numero da cor: ")
    val cor = readln().toInt()

    Cor.entries.forEach { material ->
        println("${material.ordinal} - ${material.name}")
    }
    println("Digite o numero do material: ")
    val material = readln().toInt()

    println("Escolha o formato: ")
    val formato = readln()

    println("Digite o preco: ")
    val preco = readln().toBigDecimal()

    val conexao = JPA()
    conexao.salvar(
        CaixaDaAgua(
            marca = marca,
            modelo = modelo,
            dimensao = dimensao,
            cor = Cor.entries[cor],
            material = Material.entries[material],
            formato = formato,
            preco = preco
        )
        )
    }
