package com.mgcss.l2g8.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mgcss.l2g8.domain.enums.EstadoSolicitud;

@SpringBootTest
public class SolicitudTest {

	@Test
	public void cerrarSolicitudAbierta() {
		Solicitud solicitud = new Solicitud(1L, EstadoSolicitud.ABIERTA, new Date());

		solicitud.cerrarSolicitud();

		assertEquals(EstadoSolicitud.CERRADA, solicitud.getEstado());
	}

}
