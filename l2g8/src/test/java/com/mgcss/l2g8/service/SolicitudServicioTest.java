package com.mgcss.l2g8.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.l2g8.domain.Solicitud;
import com.mgcss.l2g8.domain.Tecnico;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;
import com.mgcss.l2g8.infraestructure.SolicitudRepository;
import com.mgcss.l2g8.infraestructure.TecnicoRepository;

@ExtendWith(MockitoExtension.class)
  class SolicitudServicioTest {

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private SolicitudService solicitudService;

    @Test
    void debeCrearSolicitud() {
        Solicitud solicitud = new Solicitud(1L, EstadoSolicitud.ABIERTA, new Date());
        when(solicitudRepository.save(solicitud)).thenReturn(solicitud);

        Solicitud creada = solicitudService.crearSolicitud(solicitud);

        assertEquals(solicitud, creada);
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void debeAsignarTecnicoActivoASolicitudAbierta() {
        Solicitud solicitud = new Solicitud(2L, EstadoSolicitud.ABIERTA, new Date());
        Tecnico tecnico = Tecnico.builder().id(1L).nombre("Tecnico 1").estado(EstadoTecnico.ACTIVO).build();

        when(solicitudRepository.findById(2L)).thenReturn(Optional.of(solicitud));
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico));

        solicitudService.asignarTecnico(2L, 1L);

        assertEquals(tecnico, solicitud.getTecnico());
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void noDebeAsignarTecnicoSiNoExisteSolicitud() {
        when(solicitudRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.asignarTecnico(99L, 1L));

        assertEquals("La solicitud no existe.", excepcion.getMessage());
        verify(tecnicoRepository, never()).findById(1L);
    }

    @Test
    void noDebeAsignarTecnicoSiNoExisteTecnico() {
        Solicitud solicitud = new Solicitud(3L, EstadoSolicitud.ABIERTA, new Date());
        when(solicitudRepository.findById(3L)).thenReturn(Optional.of(solicitud));
        when(tecnicoRepository.findById(44L)).thenReturn(Optional.empty());

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.asignarTecnico(3L, 44L));

        assertEquals("El tecnico no existe.", excepcion.getMessage());
        verify(solicitudRepository, never()).save(solicitud);
    }

    @Test
    void noDebeAsignarTecnicoInactivo() {
        Solicitud solicitud = new Solicitud(4L, EstadoSolicitud.ABIERTA, new Date());
        Tecnico tecnico = Tecnico.builder().id(3L).nombre("Tecnico 3").estado(EstadoTecnico.INACTIVO).build();

        when(solicitudRepository.findById(4L)).thenReturn(Optional.of(solicitud));
        when(tecnicoRepository.findById(3L)).thenReturn(Optional.of(tecnico));

        IllegalStateException excepcion = assertThrows(
                IllegalStateException.class,
                () -> solicitudService.asignarTecnico(4L, 3L));

        assertEquals("El técnico no se puede asignar porque no está activo.", excepcion.getMessage());
        verify(solicitudRepository, never()).save(solicitud);
    }

    @Test
    void noDebeAsignarTecnicoSiIdSolicitudEsNulo() {
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.asignarTecnico(null, 1L));

        assertEquals("El id de la solicitud es obligatorio.", excepcion.getMessage());
    }

    @Test
    void debeCambiarEstado() {
        Solicitud solicitud = new Solicitud(5L, EstadoSolicitud.PROCESANDO, new Date());
        when(solicitudRepository.findById(5L)).thenReturn(Optional.of(solicitud));

        solicitudService.cambiarEstado(5L, EstadoSolicitud.CERRADA);

        assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void noDebeCambiarEstadoConSolicitudInexistente() {
        when(solicitudRepository.findById(500L)).thenReturn(Optional.empty());

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.cambiarEstado(500L, EstadoSolicitud.PROCESANDO));

        assertEquals("La solicitud no existe.", excepcion.getMessage());
    }

    @Test
    void noDebeCambiarEstadoANulo() {
        Solicitud solicitud = new Solicitud(6L, EstadoSolicitud.ABIERTA, new Date());
        when(solicitudRepository.findById(6L)).thenReturn(Optional.of(solicitud));

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> solicitudService.cambiarEstado(6L, null));

        assertEquals("El estado es obligatorio.", excepcion.getMessage());
        verify(solicitudRepository, never()).save(solicitud);
    }
}
