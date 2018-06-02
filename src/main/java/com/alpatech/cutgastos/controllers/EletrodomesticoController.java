package com.alpatech.cutgastos.controllers;

import com.alpatech.cutgastos.enums.JsonReturnStatus;
import com.alpatech.cutgastos.models.Eletrodomestico;
import com.alpatech.cutgastos.models.Usuario;
import com.alpatech.cutgastos.services.EletrodomesticoService;
import com.alpatech.cutgastos.services.UsuarioService;
import com.alpatech.cutgastos.utils.JsonReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/eletrodomestico")
public class EletrodomesticoController {

    @Autowired
    private EletrodomesticoService eletrodomesticoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public JsonReturn get(@RequestParam(required = false) Integer id, @RequestParam(required = false) Integer usuarioId){
        if(id != null) {
            Eletrodomestico eletrodomestico = this.eletrodomesticoService.findById(id);
            if(eletrodomestico != null) {
                return new JsonReturn(null, JsonReturnStatus.SUCESSO, this.eletrodomesticoService.findById(id));
            }
            else{
                return new JsonReturn("Eletrodoméstico não encotrado", JsonReturnStatus.ERRO, null);
            }
        }
        else{
            List<Eletrodomestico> eletrodomesticos;
            if(usuarioId != null){
                Usuario usuario = this.usuarioService.findById(usuarioId);
                if(usuario == null){
                    return new JsonReturn("Usuário não encotrado", JsonReturnStatus.ERRO, null);
                }
                else{
                    eletrodomesticos = this.eletrodomesticoService.findAll(usuarioId);
                    if(eletrodomesticos != null && eletrodomesticos.size() > 0) {
                        return new JsonReturn(null, JsonReturnStatus.SUCESSO, eletrodomesticos);
                    }
                    else{
                        return new JsonReturn("Nenhum eletrodoméstico cadastrado para o usuário informado", JsonReturnStatus.ERRO, null);
                    }
                }
            }
            else{
                eletrodomesticos = this.eletrodomesticoService.findAll(null);
                if(eletrodomesticos != null && eletrodomesticos.size() > 0) {
                    return new JsonReturn(null, JsonReturnStatus.SUCESSO, eletrodomesticos);
                }
                else{
                    return new JsonReturn("Nenhum eletrodoméstico encontrado na base de dados", JsonReturnStatus.ERRO, null);
                }
            }
        }
    }

    @PostMapping
    public JsonReturn save(@Valid @RequestBody Eletrodomestico eletrodomestico, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return new JsonReturn("Não foi possível completar o cadastro com os dados informados!", JsonReturnStatus.ERRO, null);
        }
        else{
            if(eletrodomestico.getUsuario().getId() != null && eletrodomestico.getDataCadastro() != null && eletrodomestico.getNome() != null
                    && eletrodomestico.getPotenciaStandby() != null && eletrodomestico.getPotenciaUso() != null && eletrodomestico.getDescricao() != null
                    && eletrodomestico.getHoras() != null && eletrodomestico.getQuantidade() != null && eletrodomestico.getMinutos() != null) {
                if(eletrodomestico.getId() == null) {
                    try {
                        eletrodomestico = this.eletrodomesticoService.save(eletrodomestico);
                        if (eletrodomestico.getId() != null) {
                            return new JsonReturn(null, JsonReturnStatus.SUCESSO, null);
                        } else {
                            return new JsonReturn("Não foi possível salvar o usuário.", JsonReturnStatus.ERRO, null);
                        }
                    } catch (Exception e) {
                        return new JsonReturn("Não foi possível salvar o usuário.", JsonReturnStatus.ERRO, null);
                    }
                }
                else{
                    return new JsonReturn("Não é possível realizar o cadastro de um eletrodoméstico já existente.", JsonReturnStatus.ERRO, null);
                }
            }
            else {
                return new JsonReturn("Campos obrigatórios não foram informados", JsonReturnStatus.ERRO, null);
            }
        }
    }

    @PutMapping
    public JsonReturn update(@Valid @RequestBody Eletrodomestico eletrodomestico, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return new JsonReturn("Não foi possível completar a atualização com os dados informados!", JsonReturnStatus.ERRO, null);
        }
        else{
            try {
                Eletrodomestico eletro = this.eletrodomesticoService.findById(eletrodomestico.getId());
                if (eletro.getId() != null) {
                    eletro.setNome(eletrodomestico.getNome());
                    eletro.setDataCadastro(eletrodomestico.getDataCadastro());
                    eletro.setDescricao(eletrodomestico.getDescricao());
                    eletro.setHoras(eletrodomestico.getHoras());
                    eletro.setMinutos(eletrodomestico.getMinutos());
                    eletro.setPotenciaStandby(eletrodomestico.getPotenciaStandby());
                    eletro.setPotenciaUso(eletrodomestico.getPotenciaUso());
                    eletro.setQuantidade(eletrodomestico.getQuantidade());
                    this.eletrodomesticoService.save(eletro);
                    return new JsonReturn(null, JsonReturnStatus.SUCESSO, null);
                }
                else{
                    return new JsonReturn("Eletrodoméstico não encontrado.", JsonReturnStatus.ERRO, null);
                }
            }
            catch(Exception e){
                return new JsonReturn("Não foi possível salvar o usuário.", JsonReturnStatus.ERRO, null);
            }
        }
    }

    @DeleteMapping
    public JsonReturn delete(@RequestParam Integer id){
        Eletrodomestico eletrodomestico = this.eletrodomesticoService.findById(id);
        if(eletrodomestico != null){
            this.eletrodomesticoService.delete(eletrodomestico);
            return new JsonReturn("Eletrodomestico excluído com sucesso", JsonReturnStatus.SUCESSO, null);
        }
        else {
            return new JsonReturn("Eletrodomestico não encontrado!", JsonReturnStatus.ERRO, null);
        }
    }
}
