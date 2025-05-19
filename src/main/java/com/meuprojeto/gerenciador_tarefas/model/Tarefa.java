package com.meuprojeto.gerenciador_tarefas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 3, max = 255, message = "A descrição deve ter entre 3 e 255 caracteres")
    private String descricao;

    private boolean concluida;

    private String prioridade; //add campo prioridade

    public Tarefa(String descricao) {
        this.descricao = descricao;
        this.concluida = false;
        this.prioridade = "média"; //Valor Padrão
    }

    public Tarefa(String descricao, String prioridade) {
        this.descricao = descricao;
        this.concluida = false;
        this.prioridade = prioridade;
    }

}
