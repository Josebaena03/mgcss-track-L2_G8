package com.mgcss.l2g8.domain;

import java.util.Date;

import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Solicitud {
    
    private Long id;
    private EstadoSolicitud estado;
    private Tecnico tecnico;
    private Date fechaCreacion;

    public Solicitud(Long id, EstadoSolicitud estado, Date fechaCreacion) {
        if (fechaCreacion == null) {
            throw new IllegalArgumentException("La fecha de creacion es obligatoria.");
        }
        this.id = id;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }


    public void cerrarSolicitud() {
        if (estado == EstadoSolicitud.PROCESANDO) {
            estado = EstadoSolicitud.CERRADA;
        }
        else{
            throw new IllegalStateException("La solicitud no se puede cerrar porque no está en estado PROCESANDO.");
        }
    }

    public void asignarTecnico(Tecnico nuevoTecnico) {
        if (nuevoTecnico == null) {
            throw new IllegalArgumentException("El tecnico es obligatorio.");
        }

        if (estado == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("No se puede asignar tecnico a una solicitud cerrada.");
        }

        if (nuevoTecnico.getEstado() != EstadoTecnico.ACTIVO) {
            throw new IllegalStateException("El técnico no se puede asignar porque no está activo.");
        }

        this.tecnico = nuevoTecnico;
    }

    public void cambiarEstado(EstadoSolicitud nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado es obligatorio.");
        }

        if (nuevoEstado == EstadoSolicitud.CERRADA) {
            cerrarSolicitud();
            return;
        }

        if (estado == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("No se puede cambiar el estado de una solicitud cerrada.");
        }

        this.estado = nuevoEstado;
    }

    


}
