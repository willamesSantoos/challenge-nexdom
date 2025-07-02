# Desafio FullStack: Controle de Estoque

Este projeto é uma solução para o desafio FullStack proposto pela Nexdom, que consiste em construir uma aplicação RESTful para controle de estoque, com funcionalidades de cadastro de produtos, movimentação de estoque (entrada/saída), consultas e cálculo de lucro por produto. Resolvi esse desafio como prática de estudo.

## Tecnologias Utilizadas

- **Back-End:** Java Spring Boot
- **Front-End:** Vue.js
- **Banco de Dados:** H2 (em memória, para facilitar testes e desenvolvimento)
- **Containerização:** Docker (backend)

---

## Funcionalidades

### Produto

- **Código**
- **Descrição**
- **Tipo Produto:** Eletrônico, Eletrodoméstico, Móvel
- **Valor no Fornecedor**
- **Quantidade em Estoque**

#### Operações CRUD
- Criar, listar, atualizar e remover produtos.

---

### Movimento de Estoque

- **Produto** (associação com a entidade Produto)
- **Tipo Movimentação:** Entrada ou Saída
- **Valor de Venda**
- **Data de Venda**
- **Quantidade Movimentada**

#### Funcionalidades
- Registrar entradas e saídas de produtos no estoque.
- Validação automática do saldo em estoque ao efetuar uma saída. Caso o estoque seja insuficiente, uma mensagem específica é retornada.
- Consulta de produtos por tipo, exibindo a quantidade de saídas e a quantidade disponível em estoque.
- Consulta de lucro por produto, mostrando:
  - Quantidade total de saídas
  - Total do lucro (*valor de venda – valor do fornecedor*)

---

## Como Executar

### Back-End

#### Executando com Docker

1. Certifique-se de ter o Docker instalado em sua máquina.
2. Navegue até o diretório do backend.
3. Execute os comandos abaixo para construir e rodar a aplicação:

   ```bash
   docker build -t nexdom-backend .
   docker run -p 8080:8080 nexdom-backend
   ```

   O backend estará disponível em: `http://localhost:8080`

#### Executando Localmente (Sem Docker)

1. Certifique-se de ter o Java 17+ instalado.
2. Execute o comando:

   ```bash
   ./mvnw spring-boot:run
   ```

---

### Front-End

1. Certifique-se de ter o Node.js (16+) e npm/yarn instalados.
2. Navegue até o diretório do frontend.
3. Instale as dependências:

   ```bash
   npm install
   ```

4. Inicie a aplicação:

   ```bash
   npm run dev
   ```

   O frontend estará disponível em: `http://localhost:5173`

---
