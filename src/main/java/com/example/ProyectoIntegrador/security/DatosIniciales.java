package com.example.ProyectoIntegrador.security;

import com.example.ProyectoIntegrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.ProyectoIntegrador.entity.Usuario;
import com.example.ProyectoIntegrador.entity.UsuarioRole;

@Component
public class DatosIniciales implements ApplicationRunner {
@Autowired
private UsuarioRepository usuarioRepository;
@Autowired
private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String passSinCifrar= "admin";
        String passCifrado= passwordEncoder.encode(passSinCifrar);
        Usuario usuario= new Usuario("jorgito", UsuarioRole.ROLE_USER,passCifrado,"user@user.com","jpereyradh");
        Usuario usuario2= new Usuario("Carol", UsuarioRole.ROLE_ADMIN,passCifrado,"admin@admin.com","carol123");
        System.out.println("pass cifrado: "+passCifrado);
        usuarioRepository.save(usuario);
        usuarioRepository.save(usuario2);

    }
}
