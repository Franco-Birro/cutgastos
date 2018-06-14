package com.alpatech.cutgastos.services;

import com.alpatech.cutgastos.models.Eletrodomestico;
import com.alpatech.cutgastos.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class RelatoriosServices {

    @Autowired
    EletrodomesticoService eletrodomesticoService;

    public Double [] consumo(Integer mes, Integer ano, Integer usuarioId)
    {
        Double [] consumo;
        List<Eletrodomestico> eletrodomesticos =  eletrodomesticoService.findAll(usuarioId);
        if(mes == null){
            consumo = new Double[12];
            Util.instanciaVetorComZero(consumo);
            if(eletrodomesticos != null || eletrodomesticos.size() > 0){
                LocalDate dataCadastro;
                LocalDate dataFinal;
                for(Eletrodomestico eletrodomestico: eletrodomesticos){
                    Double potenciaPorDia = eletrodomestico.getHoras() * eletrodomestico.getPotenciaUso() * eletrodomestico.getQuantidade()/1000.0;
                    dataCadastro = Util.dateToLocalDate(eletrodomestico.getDataCadastro());
                    dataFinal = dataCadastro.plusDays(eletrodomestico.getDias()-1);
                    if(dataCadastro.getYear() == ano && dataFinal.getYear() == ano) {
                        Integer difDias;
                        if(dataCadastro.getMonthValue() == dataFinal.getMonthValue()){
                            difDias = dataFinal.getDayOfMonth() - dataCadastro.getDayOfMonth();
                            consumo[dataCadastro.getMonthValue()-1] += difDias * potenciaPorDia;
                        }
                        else{
                            difDias = Util.totalDiasNoMes(dataCadastro.getMonthValue(), ano) - dataCadastro.getDayOfMonth();
                            consumo[dataCadastro.getMonthValue()-1] += difDias * potenciaPorDia;

                            for(int i = dataCadastro.getMonthValue()+1; i < dataFinal.getMonthValue(); i++){
                                consumo[i-1] += Util.totalDiasNoMes(i, ano) * potenciaPorDia;
                            }
                            if(dataFinal.getMonthValue() > dataCadastro.getMonthValue()){
                                consumo[dataFinal.getMonthValue()-1] += dataFinal.getDayOfMonth() * potenciaPorDia;
                            }
                        }
                    }
                    else if (dataCadastro.getYear() == ano && dataFinal.getYear() > ano){
                        Integer difDias = Util.totalDiasNoMes(dataCadastro.getMonthValue(), ano) - dataCadastro.getDayOfMonth();
                        consumo[dataCadastro.getMonthValue()-1] += difDias * potenciaPorDia;

                        for(int i = dataCadastro.getMonthValue()+1; i <= 12; i++){
                            consumo[i-1] += Util.totalDiasNoMes(i, ano) * potenciaPorDia;
                        }
                    }
                    else if (dataCadastro.getYear() < ano && dataFinal.getYear() > ano){
                        for(int i = 1; i <= 12; i++){
                            consumo[i-1] += Util.totalDiasNoMes(i+1, ano) * potenciaPorDia;
                        }
                    }
                    else if (dataFinal.getYear() == ano){
                        for(int i = 1; i < dataFinal.getMonthValue(); i ++){
                            consumo[i-1] += Util.totalDiasNoMes(i, ano) * potenciaPorDia;
                        }
                        consumo[dataFinal.getMonthValue()-1] += dataFinal.getDayOfMonth() * potenciaPorDia;
                    }
                }
            }
        }
        else
        {
            consumo = new Double[Util.totalDiasNoMes(mes, ano)];
            Util.instanciaVetorComZero(consumo);
            if(eletrodomesticos != null || eletrodomesticos.size() > 0){
                LocalDate dataCadastro;
                LocalDate dataFinal;
                Integer initialDay=0;
                Integer finalDay=0;
                for(Eletrodomestico eletrodomestico: eletrodomesticos) {
                    Double potenciaPorDia = eletrodomestico.getHoras() * eletrodomestico.getPotenciaUso() * eletrodomestico.getQuantidade()/1000.0;
                    dataCadastro = Util.dateToLocalDate(eletrodomestico.getDataCadastro());
                    dataFinal = dataCadastro.plusDays(eletrodomestico.getDias()-1);

                    if (dataCadastro.getYear() == ano && dataFinal.getYear() == ano) {
                        if (mes == dataCadastro.getMonthValue() && dataCadastro.getMonthValue() == dataFinal.getMonthValue()) {
                            initialDay = dataCadastro.getDayOfMonth();
                            finalDay = dataFinal.getDayOfMonth();
                        } else if (mes == dataCadastro.getMonthValue() && mes < dataFinal.getMonthValue()) {
                            initialDay = dataCadastro.getDayOfMonth();
                            finalDay = Util.totalDiasNoMes(mes, ano);
                        } else if (mes > dataCadastro.getMonthValue() && mes < dataFinal.getMonthValue()) {
                            initialDay = 1;
                            finalDay = Util.totalDiasNoMes(mes, ano);
                        } else if (mes > dataCadastro.getMonthValue() && mes == dataFinal.getMonthValue()) {
                            initialDay = 1;
                            finalDay = dataFinal.getDayOfMonth();
                        }
                        if (initialDay != 0) {
                            for (int i = initialDay; i <= finalDay; i++) {
                                consumo[i - 1] += potenciaPorDia;
                            }
                        }
                    }
                    else if (dataCadastro.getYear() == ano && dataFinal.getYear() > ano){
                        if (mes == dataCadastro.getMonthValue()) {
                            initialDay = dataCadastro.getDayOfMonth();
                            finalDay = Util.totalDiasNoMes(mes, ano);
                        } else if (mes > dataCadastro.getMonthValue()) {
                            initialDay = 1;
                            finalDay = Util.totalDiasNoMes(mes, ano);
                        }
                        if (initialDay != 0) {
                            for (int i = initialDay; i <= finalDay; i++) {
                                consumo[i - 1] += potenciaPorDia;
                            }
                        }
                    }
                    else if (dataCadastro.getYear() < ano && dataFinal.getYear() == ano){
                        if(mes == dataFinal.getMonthValue()){
                            initialDay = 1;
                            finalDay = dataFinal.getMonthValue();
                        }
                        else if(mes < dataFinal.getMonthValue()){
                            initialDay = 1;
                            finalDay = Util.totalDiasNoMes(mes, ano);
                        }
                        if (initialDay != 0) {
                            for (int i = initialDay; i <= finalDay; i++) {
                                consumo[i - 1] += potenciaPorDia;
                            }
                        }
                    }
                    else if (dataCadastro.getYear() < ano && dataFinal.getYear() > ano){
                        for (int i = 1; i <= consumo.length; i++) {
                            consumo[i - 1] += potenciaPorDia;
                        }
                    }
                }
            }
        }
        return consumo;
    }

    public HashMap<String, Object> maioresConsumidores(Integer usuarioId){
        List<Eletrodomestico> eletrodomesticos = this.eletrodomesticoService.findAll(usuarioId);
        if(eletrodomesticos == null || eletrodomesticos.size() ==0){
            return null;
        }
        else{
            List<String> nomes = new ArrayList<>();
            List<Double> gastos = new ArrayList<>();

            Double potencia = 0.0;
            LocalDate ultimoDia;

            for(Eletrodomestico eletrodomestico: eletrodomesticos){
                potencia = eletrodomestico.getPotenciaUso() * eletrodomestico.getHoras() * eletrodomestico.getDias() / 1000.0;

                Boolean isMaior = false;
                for(int i = 0; i < gastos.size(); i++){
                    if(potencia > gastos.get(i)){
                        gastos.add(i,potencia);
                        nomes.add(i,eletrodomestico.getNome());
                        isMaior = true;
                        break;
                    }
                }

                if(isMaior == false){
                    gastos.add(potencia);
                    nomes.add(eletrodomestico.getNome());
                }
            }

            List<String> nomesMaiores = new ArrayList<>();
            List<Double> gastosMaiores = new ArrayList<>();
            for(int i = 0; i < gastos.size() && i < 5; i++){
                nomesMaiores.add(nomes.get(i));
                gastosMaiores.add(gastos.get(i));
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("labels", nomesMaiores);
            map.put("gastos", gastosMaiores);

            return  map;
        }
    }
}
