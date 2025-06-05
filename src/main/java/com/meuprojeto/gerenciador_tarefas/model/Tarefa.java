package com.meuprojeto.gerenciador_tarefas.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da tarefa")
    private Long id;

    @Schema(description = "Descrição da tarefa")
    private String descricao;

    @Schema(description = "Indica se a tarefa foi concluída")
    private boolean concluida = false;

    @Schema(description = "Nível de prioridade da tarefa (baixa, média, alta)")
    private String prioridade;


    public Tarefa() {
    }

    public Tarefa(String descricao, String prioridade) {
        this.descricao = descricao;
        this.prioridade = prioridade;
    }

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;


    // Getters e setters corrigidos para as novas datas
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt; // CORRIGIDO: de updateAt para updatedAt
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt; // CORRIGIDO: de updateAt para updatedAt
    }


    // Métodos de callback para preencher as datas automaticamente
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now(); // CORRIGIDO: de updateAt para updatedAt
    }

    @PreUpdate
    protected void onUpdate() { // CORRIGIDO: de onUpadate para onUpdate
        updatedAt = LocalDateTime.now(); // CORRIGIDO: de updateAt para updatedAt
    }
}