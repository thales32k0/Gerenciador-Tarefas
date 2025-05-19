package com.meuprojeto.gerenciador_tarefas.Service;

import com.meuprojeto.gerenciador_tarefas.model.Tarefa;
import org.springframework.stereotype.Service;
import com.meuprojeto.gerenciador_tarefas.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com o ID: " + id ));
    }

    public Tarefa criar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa atualizar(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefaExistente = buscarPorId(id);
        tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
        tarefaExistente.setConcluida(tarefaAtualizada.isConcluida());
        return tarefaRepository.save(tarefaExistente);
    }

    public void deletar(Long id) {
        tarefaRepository.deleteById(id);
    }
}
