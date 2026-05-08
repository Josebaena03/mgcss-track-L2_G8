package com.mgcss.l2g8.domain;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

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
    private List<RegistroEstado> historialEstados = new ArrayList<>();

    public Solicitud(Long id, EstadoSolicitud estado, Date fechaCreacion) {
        validarFechaCreacion(fechaCreacion);
        this.id = id;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        registrarCambioDeEstado(estado);
    }


    public void cerrarSolicitud() {
        validarQueSePuedeCerrar();
        this.estado = EstadoSolicitud.CERRADA;
        registrarCambioDeEstado(this.estado);
    }

    public void reabrir() {
        if (estado != EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("Solo se puede reabrir una solicitud cerrada.");
        }
        cambiarEstado(EstadoSolicitud.PROCESANDO);
    }

    public void asignarTecnico(Tecnico nuevoTecnico) {
        validarTecnico(nuevoTecnico);
        validarQueLaSolicitudNoEsteCerrada();
        validarTecnicoActivo(nuevoTecnico);

        this.tecnico = nuevoTecnico;
    }

    public void cambiarEstado(EstadoSolicitud nuevoEstado) {
        validarNuevoEstado(nuevoEstado);

        if (nuevoEstado == EstadoSolicitud.CERRADA) {
            cerrarSolicitud();
            return;
        }

        if (!estado.puedeCambiarA(nuevoEstado)) {
            throw new IllegalStateException("No se puede cambiar el estado de una solicitud cerrada.");
        }

        this.estado = nuevoEstado;
        registrarCambioDeEstado(this.estado);
    }

    private void registrarCambioDeEstado(EstadoSolicitud nuevoEstado) {
        this.historialEstados.add(new RegistroEstado(nuevoEstado, new Date()));
    }

    private void validarFechaCreacion(Date fechaCreacion) {
        if (fechaCreacion == null) {
            throw new IllegalArgumentException("La fecha de creacion es obligatoria.");
        }
    }

    private void validarQueSePuedeCerrar() {
        if (!estado.permiteCerrar()) {
            throw new IllegalStateException("La solicitud no se puede cerrar porque no está en estado PROCESANDO.");
        }
    }

    private void validarTecnico(Tecnico tecnico) {
        if (tecnico == null) {
            throw new IllegalArgumentException("El tecnico es obligatorio.");
        }
    }

    private void validarQueLaSolicitudNoEsteCerrada() {
        if (estado == EstadoSolicitud.CERRADA) {
            throw new IllegalStateException("No se puede asignar tecnico a una solicitud cerrada.");
        }
    }

    private void validarTecnicoActivo(Tecnico tecnico) {
        if (!tecnico.getEstado().esActivo()) {
            throw new IllegalStateException("El técnico no se puede asignar porque no está activo.");
        }
    }

    private void validarNuevoEstado(EstadoSolicitud nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado es obligatorio.");
        }
    }

    


}
