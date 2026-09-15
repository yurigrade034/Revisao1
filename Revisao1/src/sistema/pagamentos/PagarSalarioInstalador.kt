package sistema.pagamentos

import enumeradores.TipoMovimentacao
import financeiro.Movimentacao
import repository.CRUDCaixa
import repository.CRUDInstalador
import repository.CRUDMovimentacao
import sistema.util.lerDecimal
import sistema.util.lerInteiro
import sistema.util.lerTexto
import java.time.LocalDateTime

fun pagarSalarioInstalador() {
    println("\n--- PAGAMENTO DE SALÁRIO DE INSTALADOR ---")

    val instaladorCRUD = CRUDInstalador()

    // Exibe a lista para o usuário visualizar os IDs disponíveis
    println("\nInstaladores cadastrados:")
    instaladorCRUD.listar()

    val idInstalador = lerInteiro("Digite o ID do Instalador: ")

    // Validação de existência no banco
    if (!instaladorCRUD.existePorId(idInstalador)) {
        println("Erro: Não foi encontrado nenhum instalador com o ID $idInstalador!")
        return
    }

    val valorSalario = lerDecimal("Digite o valor do salário a ser pago: ")
    val responsavel = lerTexto("Digite o nome do responsável pelo pagamento: ")

    val caixaCRUD = CRUDCaixa()
    val caixaAtual = caixaCRUD.buscarSaldoAtual()

    if (caixaAtual.dinheiro.compareTo(valorSalario) < 0) {
        println("Erro: Saldo atual (R$ ${caixaAtual.dinheiro}) é insuficiente para pagar R$ $valorSalario.")
        return
    }

    CRUDMovimentacao().salvar(
        Movimentacao(
            valor = valorSalario,
            dataMovimentacao = LocalDateTime.now(),
            contexto = TipoMovimentacao.PAGAMENTO_SALARIO,
            pagador = "EMPRESA",
            recebedor = "INSTALADOR #$idInstalador",
            responsavel = responsavel
        )
    )

    val novoSaldo = caixaAtual.dinheiro.subtract(valorSalario)
    caixaCRUD.atualizarSaldo(novoSaldo)

    println("Pagamento realizado com sucesso!")
    println("Novo saldo em caixa: R$ $novoSaldo")
}