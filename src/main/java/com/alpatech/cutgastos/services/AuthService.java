package com.alpatech.cutgastos.services;

import com.alpatech.cutgastos.models.Usuario;
import com.alpatech.cutgastos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Boolean auth(String email, String senha){
        Usuario usuario = this.usuarioRepository.findByEmail(email);
        if(usuario.getSenha().equals(senha)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
