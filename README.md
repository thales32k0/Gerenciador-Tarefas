# Gerenciador de Tarefas API

[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Swagger](https://img.shields.io/badge/Swagger-3.0-blue.svg)](https://swagger.io/)

Uma API simples para gerenciar tarefas.

## Funcionalidades Implementadas:

* CRUD básico para tarefas (Criar, Ler, Atualizar, Deletar)
* Marcação de tarefa como concluída
* Filtragem de tarefas por status de conclusão (`/api/tarefas?concluida=true` ou `/api/tarefas?concluida=false`)
* Ordenação de tarefas por descrição e prioridade (ascendente e descendente) usando o parâmetro `sort` (ex: `/api/tarefas?sort=descricao`, `/api/tarefas?sort=prioridade,desc`)
* Paginação da lista de tarefas usando os parâmetros `page` e `size` (ex: `/api/tarefas?page=0&size=10`)
* Documentação interativa da API com Swagger (OpenAPI) disponível em [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Como executar:

1.  Certifique-se de ter o Java e o Maven (ou Gradle) instalados.
2.  Clone este repositório.
3.  Navegue até a pasta do projeto.
4.  Execute o comando para iniciar a aplicação Spring Boot:
    ```bash
    mvn spring-boot:run
    ```

## Endpoints da API:

* `GET /api/tarefas`: Lista todas as tarefas (com suporte a filtragem por `concluida`, ordenação por `sort` e paginação por `page` e `size`).
* `GET /api/tarefas/{id}`: Busca uma tarefa por ID.
* `POST /api/tarefas`: Cria uma nova tarefa (requer um corpo JSON com `descricao` e `prioridade`).
* `PUT /api/tarefas/{id}`: Atualiza uma tarefa existente (requer um corpo JSON com os campos a serem atualizados: `descricao`, `concluida`, `prioridade`).
* `DELETE /api/tarefas/{id}`: Deleta uma tarefa por ID.
* `PUT /api/tarefas/{id}/concluida`: Marca uma tarefa como concluída.

## Próximos Passos (Opcional):

* Implementar filtragem por prioridade.(será implementado)
* Adicionar timestamps de criação e atualização.(será implementado)
* Implementar categorias/tags para tarefas.(será implementado)
* Reativar a validação dos dados da tarefa.(será implementado)
