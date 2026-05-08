package com.mgcss.l2g8.domain.enums;

public enum EstadoSolicitud {
    PROCESANDO,
    CERRADA,
    ABIERTA;

    public boolean permiteCerrar() {
        return this == PROCESANDO;
    }

    public boolean puedeCambiarA(EstadoSolicitud nuevoEstado) {
        if (nuevoEstado == null) return false;
        if (this == CERRADA) return nuevoEstado == PROCESANDO;
        return true;
    }
}
