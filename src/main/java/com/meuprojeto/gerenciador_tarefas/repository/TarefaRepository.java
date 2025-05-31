package com.meuprojeto.gerenciador_tarefas.repository;

import com.meuprojeto.gerenciador_tarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Page<Tarefa> findByConcluida(boolean concluida, Pageable pageable);
    Page<Tarefa> findAll(Pageable pageable); //Novo método para ordenação
    Page<Tarefa> findByPrioridade(String prioridade, Pageable pageable);


}
