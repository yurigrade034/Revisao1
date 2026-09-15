package sistema.pagamentos

import repository.CRUDMovimentacao

//Confere se o histórico de Movimentações bate com o saldo real do Caixa —
//recalcula tudo a partir do zero e compara (aprovado/reprovado). Não depende
//de nenhuma pessoa/role específica, é só uma consulta de conferência.
fun auditarCaixa() {
    CRUDMovimentacao().auditar()
}
