package com.mgcss.l2g8.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.mgcss.l2g8.domain.enums.EstadoTecnico;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;

class SolicitudTest {

	@Test
    void cerrarSolicitudAbierta() {
		Solicitud solicitud = new Solicitud(1L, EstadoSolicitud.PROCESANDO, new Date());

		solicitud.cerrarSolicitud();

		assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
	}

	@Test
    void noDebeCerrarSolicitudSiNoEstaProcesando() {
		Solicitud solicitud = new Solicitud(5L, EstadoSolicitud.ABIERTA, new Date());

		IllegalStateException excepcion = assertThrows(IllegalStateException.class, solicitud::cerrarSolicitud);

		assertEquals("La solicitud no se puede cerrar porque no está en estado PROCESANDO.", excepcion.getMessage());
	}

	@Test
    void noDebeCrearSolicitudSinFechaCreacion() {
		IllegalArgumentException excepcion = assertThrows(
				IllegalArgumentException.class,
				() -> new Solicitud(2L, EstadoSolicitud.ABIERTA, null));

		assertEquals("La fecha de creacion es obligatoria.", excepcion.getMessage());
	}

	@Test
    void cambiarEstadoDebePermitirPasarAProcesando() {
		Solicitud solicitud = new Solicitud(3L, EstadoSolicitud.ABIERTA, new Date());

		solicitud.cambiarEstado(EstadoSolicitud.PROCESANDO);

		assertEquals(EstadoSolicitud.PROCESANDO, solicitud.getEstado());
	}

	@Test
    void noDebeCambiarEstadoSiSolicitudYaEstaCerrada() {
		Solicitud solicitud = new Solicitud(4L, EstadoSolicitud.CERRADA, new Date());

		IllegalStateException excepcion = assertThrows(
				IllegalStateException.class,
				() -> solicitud.cambiarEstado(EstadoSolicitud.PROCESANDO));

		assertEquals("No se puede cambiar el estado de una solicitud cerrada.", excepcion.getMessage());
	}

	@Test
    void noDebeAsignarTecnicoNulo() {
		Solicitud solicitud = new Solicitud(6L, EstadoSolicitud.ABIERTA, new Date());

		IllegalArgumentException excepcion = assertThrows(
				IllegalArgumentException.class,
				() -> solicitud.asignarTecnico(null));

		assertEquals("El tecnico es obligatorio.", excepcion.getMessage());
		assertNull(solicitud.getTecnico());
	}

	@Test
	void noDebeAsignarTecnicoSiSolicitudEstaCerrada() {
		Solicitud solicitud = new Solicitud(7L, EstadoSolicitud.CERRADA, new Date());
		Tecnico tecnico = Tecnico.builder().id(10L).nombre("Tecnico 10").estado(EstadoTecnico.ACTIVO).build();

		IllegalStateException excepcion = assertThrows(
				IllegalStateException.class,
				() -> solicitud.asignarTecnico(tecnico));

		assertEquals("No se puede asignar tecnico a una solicitud cerrada.", excepcion.getMessage());
	}

}
