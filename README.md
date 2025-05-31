# Gerenciador de Tarefas API

[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Swagger](https://img.shields.io/badge/Swagger-3.0-blue.svg)](https://swagger.io/)
[![H2 Database](https://img.shields.io/badge/H2%20Database-In--Memory-blue.svg?logo=h2&logoColor=white)](http://www.h2database.com/html/main.html)
[![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-web.svg?label=Spring%20Web)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
[![Maven Central](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-starter-data-jpa.svg?label=Spring%20Data%20JPA)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Uma API simples para gerenciar tarefas.

## Tecnologias Utilizadas:
* **Spring Web:** Para construção da API REST.
* **Spring Data JPA:** Para persistência de dados utilizando o padrão JPA.
* **H2 Database:** Banco de dados em memória para desenvolvimento e testes.
* **Swagger (OpenAPI):** Para documentação interativa da API.


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

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## 👨‍💻 Autor
<table>
  <tr>
    <td><img src="https://avatars.githubusercontent.com/u/89024257?v=4" width="100"/></td>
    <td>
      <b>Thales Eduardo</b><br/>
      Entusiasta de tecnologia focado em Java ☕<br/>
      <a href="https://github.com/thales32k0">GitHub</a>
    </td>
  </tr>
</table>

## Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.