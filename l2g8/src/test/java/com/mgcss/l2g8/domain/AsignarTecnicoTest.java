package com.mgcss.l2g8.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;

public class AsignarTecnicoTest {

    @Test
    void debeAsignarTecnicoActivoASolicitudAbierta() {
        Solicitud solicitud = new Solicitud(10L, EstadoSolicitud.ABIERTA, new Date());
        Tecnico tecnico = Tecnico.builder().id(1L).nombre("Tecnico 1").estado(EstadoTecnico.ACTIVO).build();

        solicitud.asignarTecnico(tecnico);

        assertEquals(tecnico, solicitud.getTecnico());
    }

    @Test
    void noDebeAsignarTecnicoInactivo() {
        Solicitud solicitud = new Solicitud(11L, EstadoSolicitud.ABIERTA, new Date());
        Tecnico tecnico = Tecnico.builder().id(2L).nombre("Tecnico 2").estado(EstadoTecnico.INACTIVO).build();

        IllegalStateException excepcion = assertThrows(
                IllegalStateException.class,
                () -> solicitud.asignarTecnico(tecnico));

        assertEquals("El técnico no se puede asignar porque no está activo.", excepcion.getMessage());
    }
}