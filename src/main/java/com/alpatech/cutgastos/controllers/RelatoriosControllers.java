package com.alpatech.cutgastos.controllers;

import com.alpatech.cutgastos.enums.JsonReturnStatus;
import com.alpatech.cutgastos.services.RelatoriosServices;
import com.alpatech.cutgastos.utils.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/relatorio")
public class RelatoriosControllers {

    @Autowired
    RelatoriosServices relatoriosServices;

    @GetMapping(name = "/consumo")
    public JsonReturn mesOuDia(@RequestParam(name = "ano", required = false) Integer ano, @RequestParam(name = "mes", required = false) Integer mes,
                               @RequestParam (name="usuarioId") Integer usuarioId){
        if(ano == null){
            return new JsonReturn("Filtro de ano é obrigatório", JsonReturnStatus.ERRO, null);
        }
        else{
            return new JsonReturn("", JsonReturnStatus.SUCESSO,this.relatoriosServices.consumo(mes,ano, usuarioId));
        }
    }
}
