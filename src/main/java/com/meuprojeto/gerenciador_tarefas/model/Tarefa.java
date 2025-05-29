package com.meuprojeto.gerenciador_tarefas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull; // Importe esta anotação
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da tarefa")
    private Long id;

    @JsonProperty("descricao")
    //@NotBlank(message = "A descrição é obrigatória")
    //@Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres")
    @Schema(description = "Descrição da tarefa")
    private String descricao;

    @JsonProperty("concluida")
    @Schema(description = "Indica se a tarefa foi concluída")
    private boolean concluida = false;

    @JsonProperty("prioridade")
    //@NotNull(message = "A prioridade é obrigatória") // Adicione esta linha
    @Schema(description = "Nível de prioridade da tarefa (baixa, média, alta)")
    private String prioridade;

    // Construtores, getters e setters...

    public Tarefa() {
    }

    public Tarefa(String descricao, String prioridade) {
        this.descricao = descricao;
        this.prioridade = prioridade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}
