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

    public Integer [] consumo(Integer mes, Integer ano, Integer usuarioId)
    {
        Integer [] consumo;
        List<Eletrodomestico> eletrodomesticos =  eletrodomesticoService.findAll(usuarioId);
        if(mes == null){
            consumo = new Integer[12];
            Util.instanciaVetorComZero(consumo);
            if(eletrodomesticos != null || eletrodomesticos.size() > 0){
                LocalDate dataCadastro;
                LocalDate dataFinal;
                for(Eletrodomestico eletrodomestico: eletrodomesticos){
                    Integer potenciaPorDia = eletrodomestico.getHoras() * eletrodomestico.getPotenciaUso() * eletrodomestico.getQuantidade();
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
            consumo = new Integer[Util.totalDiasNoMes(mes, ano)];
            Util.instanciaVetorComZero(consumo);
            if(eletrodomesticos != null || eletrodomesticos.size() > 0){
                LocalDate dataCadastro;
                LocalDate dataFinal;
                Integer initialDay=0;
                Integer finalDay=0;
                for(Eletrodomestico eletrodomestico: eletrodomesticos) {
                    Integer potenciaPorDia = eletrodomestico.getHoras() * eletrodomestico.getPotenciaUso() * eletrodomestico.getQuantidade();
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
}
