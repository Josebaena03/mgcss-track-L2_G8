package com.mgcss.l2g8.domain.enums;

public enum EstadoTecnico {
    ACTIVO,
    INACTIVO;

    public boolean esActivo() {
        return this == ACTIVO;
    }
}