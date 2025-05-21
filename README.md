# Gerenciador de Tarefas API

[![Java](https://img.shields.io/badge/Java-8-blue?logo=openjdk)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![Swagger](https://img.shields.io/badge/Swagger-3.0-yellow?logo=swagger)](https://swagger.io/)

Uma API simples para gerenciar tarefas.

## Funcionalidades Implementadas:

- CRUD básico para tarefas (Criar, Ler, Atualizar, Deletar)
- Marcação de tarefa como concluída
- Filtragem de tarefas por status de conclusão
- Ordenação de tarefas por descrição e prioridade (ascendente e descendente)
- Paginação da lista de tarefas
- Documentação da API com Swagger (OpenAPI)

## Como executar:

1. Certifique-se de ter o Java e o Maven (ou Gradle) instalados.
2. Clone este repositório.
3. Navegue até a pasta do projeto.
4. Execute o comando para iniciar a aplicação Spring Boot:
   ```bash
   mvn spring-boot:run

Documentação da API:
A documentação interativa da API pode ser acessada em http://localhost:8080/swagger-ui/index.html

