package produto

//Estoque é só um espelho de leitura: quem cria/soma/subtrai é a Compra e a Venda.
class Estoque(
    val id: Int,
    val descricao: String,
    val quantidade: Int,
    val idCaixaDaAgua: Int
)
