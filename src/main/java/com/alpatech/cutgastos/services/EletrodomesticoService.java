package com.alpatech.cutgastos.services;

import com.alpatech.cutgastos.models.Eletrodomestico;
import com.alpatech.cutgastos.repositories.EletrodomesticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EletrodomesticoService {
    @Autowired
    private EletrodomesticoRepository eletrodomesticoRepository;

    @Transactional
    public Eletrodomestico findById(Integer id){
        return this.eletrodomesticoRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Eletrodomestico> findAll(Integer usuarioId){
        if(usuarioId == null) {
            return this.eletrodomesticoRepository.findAll();
        }
        else{
            return this.eletrodomesticoRepository.findAllByUsuarioId(usuarioId);
        }
    }

    @Transactional
    public Eletrodomestico save(Eletrodomestico eletrodomestico){
        return this.eletrodomesticoRepository.save(eletrodomestico);
    }

    @Transactional
    public void delete(Eletrodomestico eletrodomestico){
        this.eletrodomesticoRepository.delete(eletrodomestico);
    }
}
