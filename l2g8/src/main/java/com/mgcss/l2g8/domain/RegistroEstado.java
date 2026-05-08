package com.mgcss.l2g8.domain;

import java.util.Date;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import lombok.Getter;

@Getter
public class RegistroEstado {
    private EstadoSolicitud estado;
    private Date fecha;

    public RegistroEstado(EstadoSolicitud estado, Date fecha) {
        this.estado = estado;
        this.fecha = fecha;
    }

    protected RegistroEstado() {
        // Para JPA
    }
}
