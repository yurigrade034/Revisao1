package sistema.caixadaagua

import enumeradores.Cor
import enumeradores.Material
import produto.CaixaDaAgua
import repository.CRUDCaixaDaAgua
import sistema.util.lerDecimal
import sistema.util.lerDouble
import sistema.util.lerInteiro
import sistema.util.lerOpcaoEnum
import sistema.util.lerTexto

fun editarCaixa() {
    val conexao = CRUDCaixaDaAgua()
    conexao.listar()

    val id = lerInteiro("Digite o id da caixa de água que deseja editar: ")

    if (!conexao.existePorId(id)) {
        println("Caixa d'água com ID $id não foi encontrada!")
        return
    }

    val marca = lerTexto("Digite a nova marca: ")
    val modelo = lerTexto("Digite o NOVO modelo: ")
    val formato = lerTexto("Digite o NOVO formato: ")

    val largura = lerDouble("Digite a NOVA largura: ")
    val altura = lerDouble("Digite a NOVA altura: ")
    val profundidade = lerDouble("Digite a NOVA profundidade: ")
    val dimensao = mutableListOf(largura, altura, profundidade)

    val cor = lerOpcaoEnum("Escolha a NOVA cor: ", Cor.entries.toTypedArray())
    val material = lerOpcaoEnum("Escolha o NOVO material: ", Material.entries.toTypedArray())

    val preco = lerDecimal("Digite o novo preço: ")

    conexao.editar(
        CaixaDaAgua(
            marca = marca,
            modelo = modelo,
            formato = formato,
            dimensao = dimensao,
            preco = preco,
            cor = cor,
            material = material
        ), id
    )
}