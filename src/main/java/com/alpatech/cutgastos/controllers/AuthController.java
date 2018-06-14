package com.alpatech.cutgastos.controllers;

import com.alpatech.cutgastos.enums.JsonReturnStatus;
import com.alpatech.cutgastos.models.Usuario;
import com.alpatech.cutgastos.services.AuthService;
import com.alpatech.cutgastos.utils.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping(value = "/auth")
public class AuthController {

    //@RequestBody
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JsonReturn auth(@RequestBody Usuario user){
        Usuario usuario = authService.auth(user.getEmail(),user.getSenha());
        if(usuario !=null){
            return new JsonReturn("Autenticado com Sucesso!", JsonReturnStatus.SUCESSO, usuario);
        }
        return new JsonReturn("Acesso não permitido! Usuário ou senha incorretos", JsonReturnStatus.ERRO, null);
    }

    @Autowired
    private AuthService authService;
}
