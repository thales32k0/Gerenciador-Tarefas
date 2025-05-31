package com.meuprojeto.gerenciador_tarefas.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    // @Column(name = "role")
    // private Set<String> roles;

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // public Set<String> getRoles() {
    //     return roles;
    // }

    // public void setRoles(Set<String> roles) {
    //     this.roles = roles;
    // }
}