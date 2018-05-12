package com.alpatech.cutgastos.controllers;

import com.alpatech.cutgastos.enums.JsonReturnStatus;
import com.alpatech.cutgastos.services.AuthService;
import com.alpatech.cutgastos.utils.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping()
    public JsonReturn auth(@RequestParam String email, @RequestParam String senha){
        if(authService.auth(email,senha)){
            return new JsonReturn("Autenticado com Sucesso!", JsonReturnStatus.SUCESSO, null);
        }
        return new JsonReturn("Acesso não permitido! Usuário ou senha incorretos", JsonReturnStatus.ERRO, null);
    }
}
