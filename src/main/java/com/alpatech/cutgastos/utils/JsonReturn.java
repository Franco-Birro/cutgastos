package com.alpatech.cutgastos.utils;

import com.alpatech.cutgastos.enums.JsonReturnStatus;

public class JsonReturn {
    public String message;
    public JsonReturnStatus status;
    public Object data;

    public JsonReturn (String message, JsonReturnStatus status, Object data){
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
