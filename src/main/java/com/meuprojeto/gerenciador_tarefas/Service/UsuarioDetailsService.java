package com.meuprojeto.gerenciador_tarefas.Service;

import com.meuprojeto.gerenciador_tarefas.model.Usuario;
import com.meuprojeto.gerenciador_tarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o nome: " + username));

        // Aqui podemos adicionar as roles do usuário, se tivermos um campo para isso
        // Por enquanto, vamos apenas criar um usuário com a role 'USER'
        return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    }
}