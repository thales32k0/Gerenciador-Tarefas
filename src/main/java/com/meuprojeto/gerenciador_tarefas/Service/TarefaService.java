package com.meuprojeto.gerenciador_tarefas.Service;

import com.meuprojeto.gerenciador_tarefas.model.Tarefa;
import com.meuprojeto.gerenciador_tarefas.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    @Autowired
    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Page<Tarefa> listarTodasPaginada(Pageable pageable) {
        return tarefaRepository.findAll(pageable);
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public List<Tarefa> listarTodasOrdenada(String campo) {
        Sort sort = Sort.by(Sort.Direction.ASC, campo);
        return tarefaRepository.findAll(sort);
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com o ID: " + id));
    }

    public Tarefa criar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa atualizar(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefaExistente = buscarPorId(id);
        tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
        tarefaExistente.setConcluida(tarefaAtualizada.isConcluida());
        tarefaExistente.setPrioridade(tarefaAtualizada.getPrioridade());
        return tarefaRepository.save(tarefaExistente);
    }

    public void deletar(Long id) {
        tarefaRepository.deleteById(id);
    }

    public Page<Tarefa> listarPorStatusConcluidaPaginada(boolean concluida, Pageable pageable) {
        return (Page<Tarefa>) tarefaRepository.findByConcluida(concluida, pageable);
    }

    public List<Tarefa> listarPorStatusConcluida(boolean concluida) {
        return tarefaRepository.findByConcluida(concluida, Pageable.unpaged());
    }
}
