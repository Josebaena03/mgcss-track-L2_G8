package com.mgcss.l2g8.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.mgcss.l2g8.domain.enums.EstadoSolicitud;

public class SolicitudTest {

	@Test
	public void cerrarSolicitudAbierta() {
		Solicitud solicitud = new Solicitud(1L, EstadoSolicitud.PROCESANDO, new Date());

		solicitud.cerrarSolicitud();

		assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
	}

	@Test
	public void noDebeCerrarSolicitudSiNoEstaProcesando() {
		Solicitud solicitud = new Solicitud(5L, EstadoSolicitud.ABIERTA, new Date());

		IllegalStateException excepcion = assertThrows(IllegalStateException.class, solicitud::cerrarSolicitud);

		assertEquals("La solicitud no se puede cerrar porque no está en estado PROCESANDO.", excepcion.getMessage());
	}

	@Test
	public void noDebeCrearSolicitudSinFechaCreacion() {
		IllegalArgumentException excepcion = assertThrows(
				IllegalArgumentException.class,
				() -> new Solicitud(2L, EstadoSolicitud.ABIERTA, null));

		assertEquals("La fecha de creacion es obligatoria.", excepcion.getMessage());
	}

}
