package sistema.caixadaagua
import enumeradores.Cor


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
    val dimensao = listOf<Double>(largura, altura, profundidade)

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
    }
