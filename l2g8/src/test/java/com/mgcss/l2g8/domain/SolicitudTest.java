package com.mgcss.l2g8.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mgcss.l2g8.domain.enums.Estado;

@SpringBootTest
public class SolicitudTest {

	@Test
	public void cerrarSolicitudAbierta() {
		Solicitud solicitud = new Solicitud(1L, Estado.ABIERTA, new Date());

		solicitud.cerrarSolicitud();

		assertEquals(Estado.CERRADA, solicitud.getEstado());
	}

}
