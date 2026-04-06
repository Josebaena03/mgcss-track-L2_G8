package com.mgcss.l2g8.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mgcss.l2g8.domain.enums.EstadoTecnico;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;

@SpringBootTest
public class AsignarTecnicoTest {

	@Test
	public void AsignarSoloUnTecnico() {
		
		Tecnico tecnico1,tecnico2;

		tecnico1 = Tecnico.builder().id(1L).nombre("Tecnico 1").estado(EstadoTecnico.ACTIVO).build();
		tecnico2 = Tecnico.builder().id(2L).nombre("Tecnico 2").estado(EstadoTecnico.INACTIVO).build();

		Solicitud solicitud1 = new Solicitud(1L, EstadoSolicitud.ABIERTA, new Date());
		solicitud1.asignarTecnico(tecnico1);

		assertEquals(tecnico1, solicitud1.getTecnico());

		Solicitud solicitud2 = new Solicitud(1L, EstadoSolicitud.ABIERTA, new Date());
		solicitud2.asignarTecnico(tecnico2);

		
		assertEquals(tecnico2, solicitud2.getTecnico());
	}

}
