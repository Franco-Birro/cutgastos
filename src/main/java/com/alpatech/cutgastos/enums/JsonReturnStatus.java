package com.alpatech.cutgastos.enums;

public enum JsonReturnStatus {
    SUCESSO("sucesso"),
    ERRO("erro");

    private final String status;

    JsonReturnStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
