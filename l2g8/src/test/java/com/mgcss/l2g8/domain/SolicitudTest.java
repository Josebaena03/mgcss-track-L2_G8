package com.mgcss.l2g8.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;

@SpringBootTest
public class SolicitudTest {

	@Test
	public void cerrarSolicitudAbierta() {
		Solicitud solicitud = new Solicitud(1L, EstadoSolicitud.PROCESANDO, new Date());
		Tecnico tecnico = Tecnico.builder().id(1L).nombre("Tecnico 1").estado(EstadoTecnico.ACTIVO).build();
		solicitud.asignarTecnico(tecnico);

		solicitud.cerrarSolicitud();

		assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
	}

	@Test
	public void noDebeCrearSolicitudSinFechaCreacion() {
		String mensajeError = "";

		try {
			new Solicitud(2L, EstadoSolicitud.ABIERTA, null);
		} catch (IllegalArgumentException e) {
			mensajeError = e.getMessage();
		}

		assertEquals("La fecha de creacion es obligatoria.", mensajeError);
	}

	@Test
	public void noDebeAsignarTecnicoNulo() {
		Solicitud solicitud = new Solicitud(3L, EstadoSolicitud.ABIERTA, new Date());
		String mensajeError = "";

		try {
			solicitud.asignarTecnico(null);
		} catch (IllegalArgumentException e) {
			mensajeError = e.getMessage();
		}

		assertEquals("El tecnico es obligatorio.", mensajeError);
	}

	@Test
	public void noDebeAsignarTecnicoASolicitudCerrada() {
		Solicitud solicitud = new Solicitud(4L, EstadoSolicitud.CERRADA, new Date());
		Tecnico tecnico = Tecnico.builder().id(2L).nombre("Tecnico 2").estado(EstadoTecnico.ACTIVO).build();
		String mensajeError = "";

		try {
			solicitud.asignarTecnico(tecnico);
		} catch (IllegalStateException e) {
			mensajeError = e.getMessage();
		}

		assertEquals("No se puede asignar tecnico a una solicitud cerrada.", mensajeError);
	}

}
