package com.meuprojeto.gerenciador_tarefas.controller;

import com.meuprojeto.gerenciador_tarefas.model.Tarefa;
import com.meuprojeto.gerenciador_tarefas.Service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

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

    @Operation(summary = "Listar todas as tarefas",
    description = "Retorna uma lista paginada e opcionalmente filtrada e ordenada de tarefas.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas(@RequestParam(value = "concluida", required = false) Boolean concluida,
                                                    @RequestParam(value = "sort", required = false) String sort,
                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable;
        if (sort != null) {
            Sort.Direction direction = Sort.Direction.ASC;
            String property = sort;
            if (sort.endsWith(",desc")) {
                direction = Sort.Direction.DESC;
                property = sort.substring(0, sort.length() - 5);
            }
            Sort ordenacao = Sort.by(direction, property);
            pageable = PageRequest.of(page, size, ordenacao);
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<Tarefa> paginaDeTarefas;
        if (concluida != null) {
            paginaDeTarefas = tarefaService.listarPorStatusConcluidaPaginada(concluida, pageable);
        } else {
            paginaDeTarefas = tarefaService.listarTodasPaginada(pageable);
        }

        return new ResponseEntity<>(paginaDeTarefas.getContent(), HttpStatus.OK);
    }

@Operation(summary = "Busca uma tarefa por ID", description = "Retorna uma única tarefa com base no ID fornecido.")
@ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso")
@ApiResponse(responseCode = "400", description = "Tarefa não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.buscarPorId(id);
             return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Cria uma nova tarefa", description = "Cria uma nova tarefa com base nos dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso")
    @PostMapping
   public ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaService.criar(tarefa);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza uma tarefa existente", description = "Atualiza uma tarefa existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Tarefa não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody Tarefa tarefaAtualizada) {
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
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            tarefaService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            //Em um cenário real você logaria um erro. más para esse exemplo, um NOT_CONTENT serve.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Marca uma tarefa como concluída", description = "Altera o status 'concluida' de uma tarefa para true.")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @PutMapping("/{id}/concluida")
    public ResponseEntity<Tarefa> marcarConcluida(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.buscarPorId(id);
            tarefa.setConcluida(true);
            Tarefa tarefaAtualizada = tarefaService.atualizar(id, tarefa);
            return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    }

