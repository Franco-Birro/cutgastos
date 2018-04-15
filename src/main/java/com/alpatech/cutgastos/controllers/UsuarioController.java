package com.alpatech.cutgastos.controllers;

import com.alpatech.cutgastos.enums.JsonReturnStatus;
import com.alpatech.cutgastos.models.Usuario;
import com.alpatech.cutgastos.services.UsuarioService;
import com.alpatech.cutgastos.utils.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(name = "/{id}")
    public JsonReturn get(@RequestParam(required = false) String id){
        if(id != null) {
            Usuario usuario = this.usuarioService.findById(id);
            if(usuario != null) {
                return new JsonReturn(null, JsonReturnStatus.SUCESSO, this.usuarioService.findById(id));
            }
            else{
                return new JsonReturn("Usuario não encotrado", JsonReturnStatus.ERRO, null);
            }
        }
        else{
            List<Usuario> usuarios = this.usuarioService.findAll();
            if(usuarios != null && usuarios.size() > 0) {
                return new JsonReturn(null, JsonReturnStatus.SUCESSO, this.usuarioService.findAll());
            }
            else{
                return new JsonReturn("Nenhum usuário encontrado na base de dados", JsonReturnStatus.ERRO, null);
            }
        }
    }

    @PostMapping
    public Object save(@Valid @RequestBody Usuario usuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return new JsonReturn("Não foi possível completar o cadastro com os dados informados!", JsonReturnStatus.ERRO, null);
        }
        else{
            usuario = this.usuarioService.save(usuario);
            if(usuario.getId() != null){
                return new JsonReturn(null, JsonReturnStatus.SUCESSO, null);
            }
            else{
                return new JsonReturn("Não foi possível salvar o usuário.", JsonReturnStatus.ERRO, null);
            }
        }
    }
}
