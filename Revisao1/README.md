# Sistema de Gestão — Caixa D'Água

Aplicação em **Kotlin** + **PostgreSQL** (via JDBC), rodando 100% em console, pra gerenciar o negócio de venda de caixas d'água: produto, estoque, clientes, instaladores, fornecedores e o fluxo de caixa de todas as operações.

Projeto acadêmico — atende aos requisitos de: menu interativo, persistência total em banco, controle de fluxo de produto (compra/venda/estoque), gestão de pessoas (funcionários divididos em setores, clientes, fornecedores), fluxo de caixa encapsulado e validação de entrada (regex, try/catch, nullable).

---

## 🛠️ Tecnologias

- **Kotlin** (projeto IntelliJ IDEA puro — arquivo `.iml`, sem Gradle/Maven)
- **PostgreSQL**, acessado via **JDBC** (`org.postgresql.Driver`)

---

## 📂 Estrutura do projeto

```
src/
├── pessoas/       → Pessoa (classe-mãe), Cliente, Instalador, Fornecedor
├── produto/       → CaixaDaAgua, Servico, Estoque
├── financeiro/    → Movimentacao, Compra, Venda, Caixa
├── enumeradores/  → Cor, Material, Turno, Habilidade, Setor, TipoMovimentacao
├── repository/    → InterfaceJPA<T> + um CRUD por entidade + ConexaoPostgres
├── sistema/       → telas de menu (uma pasta por módulo) + sistema/util (validações)
└── Main.kt        → ponto de entrada (chama menuInicial())
```

Cada CRUD implementa `InterfaceJPA<T>` (`salvar/listar/editar/excluir`), então toda entidade é tratada de forma polimórfica pelo mesmo contrato.

---

## ▶️ Como rodar

### Pré-requisitos
- IntelliJ IDEA
- PostgreSQL rodando em `localhost:5432`
- Driver JDBC do PostgreSQL configurado nas dependências do projeto (Project Structure → Libraries → adicionar `postgresql-42.7.x.jar`, via "From Maven" ou apontando pro `.jar` baixado manualmente)

### Passos
1. Abra a pasta do projeto no IntelliJ (evite manter o projeto dentro de uma pasta sincronizada pelo OneDrive — já causou `ClassNotFoundException` por travamento de arquivo).
2. Configure o banco de dados (seção abaixo).
3. Abra `src/Main.kt`.
4. Clique no ▶️ ao lado de `fun main()` (ou `Ctrl+Shift+F10`).
5. O menu interativo abre no console, com opções de `0` a `33`.

A conexão usa, por padrão, usuário `postgres` / senha `postgres` / banco `caixaDaAgua` (definido em `repository/ConexaoPostgres.kt`). Se o seu ambiente tiver outra credencial, ajuste esse arquivo antes de rodar.

---

## 🗄️ Banco de dados

O nome do banco precisa ser **exatamente** `caixaDaAgua` (letra por letra), porque a URL de conexão é `jdbc:postgresql://localhost:5432/caixaDaAgua`.

### Script completo (criação das tabelas)

```sql
-- DROP de tudo, na ordem inversa de dependência
DROP TABLE IF EXISTS movimentacao CASCADE;
DROP TABLE IF EXISTS venda CASCADE;
DROP TABLE IF EXISTS compra CASCADE;
DROP TABLE IF EXISTS estoque CASCADE;
DROP TABLE IF EXISTS servico CASCADE;
DROP TABLE IF EXISTS caixa CASCADE;
DROP TABLE IF EXISTS fornecedor CASCADE;
DROP TABLE IF EXISTS instalador CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS caixa_da_agua CASCADE;

-- Tabelas sem dependência
CREATE TABLE caixa_da_agua (
    id SERIAL PRIMARY KEY,
    marca VARCHAR(100),
    modelo VARCHAR(100),
    dimensao DOUBLE PRECISION[], -- [largura, altura, profundidade]
    cor VARCHAR(30),
    material VARCHAR(30),
    formato VARCHAR(50),
    preco NUMERIC -- usado pra calcular o valor total de Compra/Venda automaticamente
);

CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(20),
    idade INTEGER,
    "dividasAbertas" BOOLEAN,
    "parcelasAPagar" NUMERIC[]
);

CREATE TABLE instalador (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(20),
    idade INTEGER,
    salario NUMERIC,
    turno VARCHAR(30),
    habilidade VARCHAR(30),
    setor VARCHAR(30)
);

CREATE TABLE fornecedor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(20),
    idade INTEGER,
    produto_fornecido VARCHAR(150)
);

CREATE TABLE movimentacao (
    id SERIAL PRIMARY KEY,
    dinheiro NUMERIC,
    data_movimentacao TIMESTAMP,
    descricao VARCHAR(50), -- enum TipoMovimentacao (COMPRA/VENDA/PAGAMENTO_INSTALADOR)
    pagador VARCHAR(100),
    recebedor VARCHAR(100),
    responsavel VARCHAR(100)
);

-- Cada operação insere uma NOVA linha aqui (não dá UPDATE) — o saldo atual é
-- sempre a última linha (ORDER BY id DESC LIMIT 1). Isso mantém um histórico
-- de todo saldo que a empresa já teve, além do valor atual.
CREATE TABLE caixa (
    id SERIAL PRIMARY KEY,
    dinheiro NUMERIC
);

-- Tabelas que dependem das anteriores (FK)
CREATE TABLE servico (
    id SERIAL PRIMARY KEY,
    id_cliente INTEGER REFERENCES cliente(id),
    id_instalador INTEGER REFERENCES instalador(id),
    preco NUMERIC,
    data_instalacao DATE
);

CREATE TABLE estoque (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(150),
    quantidade INTEGER,
    id_caixa_da_agua INTEGER REFERENCES caixa_da_agua(id)
);

CREATE TABLE compra (
    id SERIAL PRIMARY KEY,
    id_caixa_da_agua INTEGER REFERENCES caixa_da_agua(id),
    quantidade INTEGER,
    valor NUMERIC, -- calculado automaticamente (preço da caixa d'água × quantidade)
    data_compra DATE,
    id_fornecedor INTEGER REFERENCES fornecedor(id),
    responsavel VARCHAR(100)
);

CREATE TABLE venda (
    id SERIAL PRIMARY KEY,
    id_caixa_da_agua INTEGER REFERENCES caixa_da_agua(id),
    id_cliente INTEGER REFERENCES cliente(id),
    id_instalador INTEGER REFERENCES instalador(id),
    quantidade INTEGER,
    valor NUMERIC, -- calculado automaticamente (preço da caixa d'água × quantidade)
    data_venda DATE,
    responsavel VARCHAR(100)
);

-- Saldo inicial do caixa: a única linha que entra "na mão", direto no banco.
-- Depois disso, toda mudança de saldo é feita pelo próprio sistema.
INSERT INTO caixa (dinheiro) VALUES (1000.00);
```

### Valores válidos dos enums

| Enum | Valores |
|---|---|
| `Cor` | `AZUL_FORTE`, `AZUL_FRACO`, `BRANCO`, `CINZA` |
| `Material` | `POLIETILENO`, `FIBRA_DE_VIDRO`, `INOX` |
| `Turno` | `MATUTINO`, `VESPERTINO`, `NOTURNO` |
| `Habilidade` | `INSTALACAO`, `FINANCEIRO`, `ADMINISTRATIVO`, `LOGISTICA` |
| `Setor` | `FINANCEIRO`, `LOGISTICA`, `ADMINISTRATIVO` |
| `TipoMovimentacao` | `COMPRA`, `VENDA`, `PAGAMENTO_INSTALADOR` |

---

## 🧭 Menu / funcionalidades

| Módulo | Opções | O que faz |
|---|---|---|
| Caixa D'Água | 1-4 | CRUD completo do produto (o `preco` cadastrado aqui é usado pra calcular o valor de Compra/Venda) |
| Cliente | 5-8 | CRUD completo, CPF validado por regex |
| Instalador | 9-12 | CRUD completo (funcionário, com Turno/Habilidade/Setor) |
| Fornecedor | 13-16 | CRUD completo |
| Serviço | 17-20 | Instalação vinculada a um Cliente + Instalador |
| Compra | 21-24 | Valida saldo suficiente → calcula valor (preço × quantidade) → soma no estoque → gera movimentação → **atualiza o saldo do Caixa** |
| Venda | 25-28 | Valida estoque suficiente → calcula valor (preço × quantidade) → desconta do estoque → gera movimentação → **atualiza o saldo do Caixa** |
| Estoque | 29 | Só leitura — quem altera é Compra/Venda |
| Pagamento de salário | 30 | Gera movimentação a partir do salário já cadastrado do instalador |
| Movimentações | 31-32 | Listar/excluir o histórico financeiro (nunca editável) |
| Auditar Caixa | 33 | Recalcula o saldo a partir de TODO o histórico de Movimentações e compara com o saldo real do Caixa — aponta "APROVADO" ou "REPROVADO/DIVERGÊNCIA" |

---

## 🔒 Decisões de design

- **Todo valor de Compra/Venda é calculado automaticamente** (`preço cadastrado na CaixaDaAgua × quantidade`) — o usuário nunca digita um valor "cru", reduzindo erro humano.
- **Compra valida saldo suficiente** e **Venda valida estoque suficiente** antes de prosseguir — nenhuma das duas operações roda "no escuro".
- **O Caixa é atualizado automaticamente** a cada Compra/Venda/Pagamento, sempre a partir do cálculo em cima do saldo anterior — nunca por um valor digitado livremente. Cada atualização insere uma nova linha (histórico completo de saldo), e o saldo atual é sempre a última linha.
- **Toda `Movimentacao` nasce dentro do `salvar()`** de uma Compra, Venda ou Pagamento — nunca de uma tela solta digitando um valor sem vínculo com uma operação real.
- **`Estoque` não aceita ficar negativo** — validado antes de debitar.
- **`auditarCaixa()`** cruza o histórico de `Movimentacao` com o saldo real do `Caixa`, funcionando como uma conferência independente (útil pra provar que nada "sumiu" no meio do caminho).
- **Todo CRUD segue o mesmo contrato** (`InterfaceJPA<T>`), inclusive os que têm efeitos colaterais (Compra/Venda também mexem em Estoque, Movimentação e Caixa por dentro do próprio `salvar()`).

---

## ⚠️ Limitações conhecidas

- Sem transação SQL amarrando Compra/Venda + Estoque + Movimentação + Caixa — se a conexão cair no meio, pode ficar inconsistente.
- `Pessoa.cpf` é reaproveitado pra Fornecedor também — não distingue CPF de CNPJ.
- Sem autenticação/login — o "responsável" de cada operação é digitado manualmente, não vem de uma sessão.
- Sem validação de valores negativos em salário (só o estoque e o saldo do caixa têm trava).

---

## 💬 Quer adicionar algo?

Esse README cobre o que já está implementado. Se quiser evoluir o projeto (ex: transação SQL real, um papel de "Vendedor" dedicado, módulo de manutenção, autenticação de usuário, etc.), é só puxar assunto — dá pra planejar o que faz sentido entrar antes de mexer no código.
