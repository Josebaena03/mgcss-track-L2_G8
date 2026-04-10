package com.mgcss.l2g8.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mgcss.l2g8.domain.Solicitud;
import com.mgcss.l2g8.domain.Tecnico;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;

public class SolicitudServicioTest {

    private SolicitudService solicitudService;

    @BeforeEach
    void setUp() {
        solicitudService = new SolicitudService();
    }

    @Test
    void debeAsignarTecnicoActivoASolicitudAbierta() {
        Solicitud solicitud = new Solicitud(1L, EstadoSolicitud.ABIERTA, new Date());
        Tecnico tecnico = Tecnico.builder().id(1L).nombre("Tecnico 1").estado(EstadoTecnico.ACTIVO).build();

        solicitudService.asignarTecnico(solicitud, tecnico);

        assertEquals(tecnico, solicitud.getTecnico());
    }

    @Test
    void noDebeAsignarTecnicoNulo() {
        Solicitud solicitud = new Solicitud(2L, EstadoSolicitud.ABIERTA, new Date());

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.asignarTecnico(solicitud, null));

        assertEquals("El tecnico es obligatorio.", excepcion.getMessage());
    }

    @Test
    void noDebeAsignarTecnicoASolicitudCerrada() {
        Solicitud solicitud = new Solicitud(3L, EstadoSolicitud.CERRADA, new Date());
        Tecnico tecnico = Tecnico.builder().id(2L).nombre("Tecnico 2").estado(EstadoTecnico.ACTIVO).build();

        IllegalStateException excepcion = assertThrows(
                IllegalStateException.class,
                () -> solicitudService.asignarTecnico(solicitud, tecnico));

        assertEquals("No se puede asignar tecnico a una solicitud cerrada.", excepcion.getMessage());
    }

    @Test
    void noDebeAsignarTecnicoInactivo() {
        Solicitud solicitud = new Solicitud(4L, EstadoSolicitud.ABIERTA, new Date());
        Tecnico tecnico = Tecnico.builder().id(3L).nombre("Tecnico 3").estado(EstadoTecnico.INACTIVO).build();

        IllegalStateException excepcion = assertThrows(
                IllegalStateException.class,
                () -> solicitudService.asignarTecnico(solicitud, tecnico));

        assertEquals("El técnico no se puede asignar porque no está activo.", excepcion.getMessage());
    }

    @Test
    void noDebeAsignarTecnicoSiSolicitudEsNula() {
        Tecnico tecnico = Tecnico.builder().id(4L).nombre("Tecnico 4").estado(EstadoTecnico.ACTIVO).build();

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.asignarTecnico(null, tecnico));

        assertEquals("La solicitud es obligatoria.", excepcion.getMessage());
    }

    @Test
    void debeCerrarSolicitudValidaDesdeServicio() {
        Solicitud solicitud = new Solicitud(5L, EstadoSolicitud.PROCESANDO, new Date());

        solicitudService.cerrarSolicitud(solicitud);

        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
    }

    @Test
    void noDebeCerrarSolicitudSiEsNulaDesdeServicio() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.cerrarSolicitud(null));

        assertEquals("La solicitud es obligatoria.", excepcion.getMessage());
    }
}
