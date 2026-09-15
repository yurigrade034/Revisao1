package sistema.caixadaagua

import enumeradores.Cor
import enumeradores.Material
import produto.CaixaDaAgua
import repository.CRUDCaixaDaAgua
import sistema.util.lerDecimal
import sistema.util.lerDouble
import sistema.util.lerOpcaoEnum
import sistema.util.lerTexto

fun cadastrarNovaCaixa() {
    val marca = lerTexto("Digite a marca: ")
    val modelo = lerTexto("Digite a modelo: ")

    val largura = lerDouble("Digite a largura: ")
    val altura = lerDouble("Digite a altura: ")
    val profundidade = lerDouble("Digite a profundidade: ")
    val dimensao = mutableListOf(largura, altura, profundidade)

    val cor = lerOpcaoEnum("Escolha a cor: ", Cor.entries.toTypedArray())
    val material = lerOpcaoEnum("Escolha o material: ", Material.entries.toTypedArray())

    val formato = lerTexto("Escolha o formato: ")
    val preco = lerDecimal("Qual é o preço: ")

    val conexao = CRUDCaixaDaAgua()
    conexao.salvar(
        CaixaDaAgua(
            marca = marca,
            modelo = modelo,
            dimensao = dimensao,
            cor = cor,
            material = material,
            formato = formato,
            preco = preco
        )
    )
}
