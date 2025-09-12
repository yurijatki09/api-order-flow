Projeto Order Flow API

API RESTful para gerenciamento de usuários, produtos e pedidos.

Autenticação JWT

Controle de estoque

Criação e atualização de pedidos e produtos

-----------------------------------------------------

Pré-requisitos:

Java 17 ou superior

Maven

MySQL

Postman (ou qualquer cliente HTTP para testar as requests)

------------------------------------------------------

Configuração do banco de dados:

importar o dump sql que foi enviado por email ou crie um banco de dados chamado orderflow que ao subir o projeto o spring irá criar todas as tabelas.

CREATE DATABASE orderflow;

-------------------------------------------------------

Endpoints principais:

- Autenticação

POST /auth/register → Cadastrar usuário

{
  "nome": "Pedro",
  "tipo": "USER",
  "email": "pedro@meusistema.com",
  "senha": "user123"
}

{
  "nome": "Admin",
  "tipo": "ADMIN",
  "email": "adm@meusistema.com",
  "senha": "admin123"
}


POST /auth/login → Login de usuário

{
  "email": "pedro@meusistema.com",
  "senha": "user123"
}

{
  "email": "adm@meusistema.com",
  "senha": "admin123"
}


Retorna um JWT que deve ser usado no header Authorization: Bearer <token> para acessar endpoints protegidos.

------------------------------------------------

Produtos:

POST /product → Criar produto (necessário tipo ADMIN)

{
  "nome": "Mesa de Jantar teste token",
  "descricao": "Mesa de jantar de madeira maciça com 6 lugares",
  "preco": 1800.00,
  "categoria": "MOVEIS",
  "quantidade": 5
}

categoria é um enum. {ELETRONICOS, ROUPAS, LIVROS, MOVEIS}

GET /product → Listar produtos

PATCH /product/patch/{id} → Atualizar produto

no body inserir apenas o campo que deseja ser alterado

PATCH /product/delete/{id} → Deletar produto

Ex: http://localhost:8080/product/delete/3bd916e9-2098-49dc-ae42-b8eb89c009a5

Obs: Necessário rodar o get para listar produtos, pegar o id e inserir nas requests que esperam o id como PathVariable

---------------------------------------------------

Pedidos

POST /order → Criar pedido

{
  "produtos": [
    {
      "productId": "60c8ba66-3e69-4ef9-a991-27832cd380c8",
      "quantidade": 1
    },

    {
      "productId": "76c4dce8-0076-4a78-a016-8ee552b650c5",
      "quantidade": 1
    }
  ]
}

pode enviar com um ou mais produtos... necessário obter id do produto na request GET /product

Obs: Para realizar pedidos com usuarios diferentes é necessário refazer request de login pegar o token gerado e colar no Authorization

GET /order → Listar pedidos

POST /order/{id} → Pagar pedido

Regras: se algum produto estiver sem estoque suficiente, o pedido é cancelado automaticamente, a informação é registrada no banco e no json da response contém o campo mensagem para retornar para o usuário.

------------------------------------------------------

Consultas Otimizadas

Top 5 usuários que mais compraram

GET /reports/top-users

Descrição: Retorna os 5 usuários com mais pedidos realizados.

Ticket médio dos pedidos por usuário

GET /reports/average-ticket

Descrição: Retorna o valor médio gasto por pedido de cada usuário.

Valor total faturado no mês

GET /reports/monthly-revenue

Descrição: Retorna o valor total faturado no mês atual (somatório de todos os pedidos concluídos).
