package com.meuprojeto.gerenciador_tarefas.config;

import com.meuprojeto.gerenciador_tarefas.model.Usuario;
import com.meuprojeto.gerenciador_tarefas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o usuário 'teste' já existe
            if (usuarioRepository.findByUsername("teste").isEmpty()) {
                Usuario user = new Usuario();
                user.setUsername("teste");
                user.setPassword(passwordEncoder.encode("teste"));
                usuarioRepository.save(user);
                System.out.println("Usuário 'teste' inserido no banco de dados.");
            } else {
                System.out.println("Usuário 'teste' já existe no banco de dados.");
            }
        };
    }
}
