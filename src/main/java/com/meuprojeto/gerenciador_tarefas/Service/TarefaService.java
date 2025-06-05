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
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

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
        return tarefaRepository.findByConcluida(concluida, pageable);
    }

    public List<Tarefa> listarPorStatusConcluida(boolean concluida) {
        Page<Tarefa> pagina = tarefaRepository.findByConcluida(concluida, Pageable.unpaged());
        return pagina.getContent();
    }
    public Page<Tarefa> listarPorPrioridadePaginada(String prioridade, Pageable pageable) {
        return tarefaRepository.findByPrioridade(prioridade, pageable);
    }
    public Page<Tarefa> listarTarefasComFiltros(
            Boolean concluida,
            String prioridade,
            LocalDateTime createdAtAfter,
            LocalDateTime createdAtBefore,
            LocalDateTime updatedAtAfter,
            LocalDateTime updatedAtBefore,
            Pageable pageable) {

        Specification<Tarefa> spec = Specification.where(null); // Começa com uma especificação vazia

        if (concluida != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("concluida"), concluida));
        }
        if (prioridade != null && !prioridade.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("prioridade"), prioridade));
        }
        if (createdAtAfter != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), createdAtAfter));
        }
        if (createdAtBefore != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), createdAtBefore));
        }
        if (updatedAtAfter != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("updatedAt"), updatedAtAfter));
        }
        if (updatedAtBefore != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("updatedAt"), updatedAtBefore));
        }

        return tarefaRepository.findAll(spec, pageable);
    }

}


