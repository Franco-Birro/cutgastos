package com.alpatech.cutgastos.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Util {

    public static LocalDate dateToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static void instanciaVetorComZero(Integer [] vet){
        for(int i = 0; i < vet.length; i++){
            vet[i] = 0;
        }
    }

    public static Integer totalDiasNoMes(Integer mes, Integer ano){
        Calendar calendar = new GregorianCalendar(ano, mes-1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
