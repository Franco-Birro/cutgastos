package com.alpatech.cutgastos.services;

import com.alpatech.cutgastos.models.Usuario;
import com.alpatech.cutgastos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario findById(String id){
        return this.usuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Usuario> findAll(){
       return this.usuarioRepository.findAll();
    }

    @Transactional
    public Usuario save(Usuario usuario){
        return this.usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Usuario usuario){
        this.usuarioRepository.delete(usuario);
    }
}
