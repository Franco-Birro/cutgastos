package com.alpatech.cutgastos.repositories;

import com.alpatech.cutgastos.models.Eletrodomestico;
import com.alpatech.cutgastos.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EletrodomesticoRepository extends JpaRepository<Eletrodomestico,Integer> {

    public List<Eletrodomestico> findAllByUsuarioId(Integer usuarioId);
}
