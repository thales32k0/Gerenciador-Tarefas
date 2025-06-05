package com.meuprojeto.gerenciador_tarefas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meuprojeto.gerenciador_tarefas.model.Tarefa;
import com.meuprojeto.gerenciador_tarefas.Service.TarefaService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Tarefas", description = "Gerencia as Tarefas")
@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @Autowired
    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Operation(summary = "Lista todas as tarefas",
            description = "Retorna uma lista paginada e opcionalmente filtrada e ordenada de tarefas.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Tarefa.class)),
                    examples = @ExampleObject(
                            name = "Lista de Tarefas",
                            value = "[{\"id\": 1, \"descricao\": \"Fazer compras\", \"concluida\": false, \"prioridade\": \"alta\"}, {\"id\": 2, \"descricao\": \"Ler um livro\", \"concluida\": true, \"prioridade\": \"média\"}]"
                    )
            ))
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas(
            @Parameter(description = "Filtrar tarefas por status de conclusão (true/false)")
            @RequestParam(value = "concluida", required = false) Boolean concluida,
            @Parameter(description = "Filtrar tarefas por prioridade (baixa, média, alta)")
            @RequestParam(value = "prioridade", required = false) String prioridade,
            @Parameter(description = "Filtrar tarefas criadas a partir desta data (formato: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam(value = "createdAtAfter", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAtAfter,
            @Parameter(description = "Filtrar tarefas criadas até esta data (formato: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam(value = "createdAtBefore", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAtBefore,
            @Parameter(description = "Filtrar tarefas atualizadas a partir desta data (formato: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam(value = "updatedAtAfter", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updatedAtAfter,
            @Parameter(description = "Filtrar tarefas atualizadas até esta data (formato: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam(value = "updatedAtBefore", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updatedAtBefore,
            @Parameter(description = "Ordenar por um campo (ex: descricao, prioridade, createdAt). Adicione ',desc' para ordem descendente.")
            @RequestParam(value = "sort", required = false) String sort,
            @Parameter(description = "Número da página a ser retornada (padrão: 0)")
            @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Número de itens por página (padrão: 10)")
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = buildPageable(sort, page, size); // Reutilizamos seu método buildPageable

        // Chamar um novo método de serviço que lida com todos os filtros
        Page<Tarefa> paginaDeTarefas = tarefaService.listarTarefasComFiltros(
                concluida, prioridade, createdAtAfter, createdAtBefore, updatedAtAfter, updatedAtBefore, pageable);

        return new ResponseEntity<>(paginaDeTarefas.getContent(), HttpStatus.OK);
    }

    // Seu método buildPageable atualizado para incluir ordenação explícita de asc/desc
    private Pageable buildPageable(String sort, int page, int size) {
        Sort ordenacao = Sort.unsorted();
        if (sort != null && !sort.trim().isEmpty()) {
            Sort.Direction direction = Sort.Direction.ASC;
            String property = sort;
            if (sort.endsWith(",desc")) {
                direction = Sort.Direction.DESC;
                property = sort.substring(0, sort.length() - 5);
            } else if (sort.endsWith(",asc")) {
                direction = Sort.Direction.ASC;
                property = sort.substring(0, sort.length() - 4);
            }
            ordenacao = Sort.by(direction, property);
        }
        return PageRequest.of(page, size, ordenacao);
    }

    @Operation(summary = "Busca uma tarefa por ID", description = "Retorna uma única tarefa com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Tarefa encontrada",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tarefa.class),
                    examples = @ExampleObject(
                            name = "Tarefa Encontrada",
                            value = "{\"id\": 1, \"descricao\": \"Fazer compras\", \"concluida\": false, \"prioridade\": \"alta\"}"
                    )
            ))
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.buscarPorId(id);
             return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Cria uma nova tarefa", description = "Adiciona uma nova tarefa ao sistema.")
    @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tarefa.class),
                    examples = @ExampleObject(
                            name = "Tarefa Criada",
                            value = "{\"id\": 3, \"descricao\": \"Agendar reunião\", \"concluida\": false, \"prioridade\": \"média\"}"
                    )
            ))
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Tarefa> criar(@org.springframework.web.bind.annotation.RequestBody String rawBody) {
        System.out.println("Corpo da requisição recebido:");
        System.out.println(rawBody);
        try {
            ObjectMapper mapper = new ObjectMapper();
            Tarefa tarefa = mapper.readValue(rawBody, Tarefa.class);
            Tarefa novaTarefa = tarefaService.criar(tarefa);
            return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Atualiza uma tarefa existente", description = "Atualiza os detalhes de uma tarefa com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tarefa.class),
                    examples = @ExampleObject(
                            name = "Tarefa Atualizada",
                            value = "{\"id\": 1, \"descricao\": \"Atualizar relatório mensal\", \"concluida\": true, \"prioridade\": \"alta\"}"
                    )
            ))
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@Parameter(description = "ID da tarefa a ser atualizada") @PathVariable Long id,
                                            @org.springframework.web.bind.annotation.RequestBody Tarefa tarefaAtualizada) {
        try {
            Tarefa tarefa = tarefaService.atualizar(id, tarefaAtualizada);
            return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deleta uma tarefa por ID", description = "Deleta uma tarefa existente com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@Parameter(description = "ID da tarefa a ser deletada")@PathVariable Long id) {
        try {
            tarefaService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            //Em um cenário real você logaria um erro. más para esse exemplo, um NOT_CONTENT serve.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Marca uma tarefa como concluída", description = "Altera o status 'concluida' de uma tarefa para true.")
    @ApiResponse(responseCode = "200", description = "Tarefa marcada como concluída com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tarefa.class),
                    examples = @ExampleObject(
                            name = "Tarefa Concluída",
                            value = "{\"id\": 1, \"descricao\": \"Fazer compras\", \"concluida\": true, \"prioridade\": \"alta\"}"
                    )
            ))
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @PutMapping("/{id}/concluida")
    public ResponseEntity<Tarefa> marcarConcluida(@Parameter(description = "ID da tarefa a ser marcada como concluída") @PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.buscarPorId(id);
            tarefa.setConcluida(true);
            Tarefa tarefaAtualizada = tarefaService.atualizar(id, tarefa);
            return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
@Hidden
    @PostMapping("/teste")
    public ResponseEntity<Tarefa> testeRecebimento(@RequestBody Tarefa tarefa) {
        System.out.println("Tarefa recebida no /teste:");
        System.out.println("  Descricao: " + tarefa.getDescricao());
        System.out.println("  Prioridade: " + tarefa.getPrioridade());
        return new ResponseEntity<>(tarefa, HttpStatus.OK);
    }

    }

