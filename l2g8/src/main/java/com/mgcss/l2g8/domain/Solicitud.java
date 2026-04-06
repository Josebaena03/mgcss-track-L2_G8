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

        if(nuevoTecnico.getEstado() == EstadoTecnico.ACTIVO) {
            this.tecnico = nuevoTecnico;
        }
        else {
            throw new IllegalStateException("El técnico no se puede asignar porque no está activo.");
        }
        
    }

    


}
