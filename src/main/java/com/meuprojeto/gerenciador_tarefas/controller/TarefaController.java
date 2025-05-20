package com.meuprojeto.gerenciador_tarefas.controller;

import com.meuprojeto.gerenciador_tarefas.model.Tarefa;
import com.meuprojeto.gerenciador_tarefas.Service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @Autowired
    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

   @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas(@RequestParam(value = "conluida", required = false) Boolean concluida) {
        List<Tarefa> tarefas;
        if (concluida != null){
            tarefas = tarefaService.listarPorStatusConcluida(concluida);
        } else {
            tarefas = tarefaService.listarTodas();
        }
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
   }

   @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.buscarPorId(id);
             return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
   public ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaService.criar(tarefa);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody Tarefa tarefaAtualizada) {
        try {
           Tarefa tarefa = tarefaService.atualizar(id, tarefaAtualizada);
           return new ResponseEntity<>(tarefa, HttpStatus.OK);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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

