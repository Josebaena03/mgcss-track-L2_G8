package com.mgcss.l2g8.infraestructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.l2g8.domain.Solicitud;
import com.mgcss.l2g8.domain.Tecnico;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;
import com.mgcss.l2g8.service.SolicitudService;

@SpringBootTest
@ActiveProfiles("test")
class SolicitudCoverageIntegrationTest {

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private JpaSolicitudRepositoryAdapter solicitudRepositoryAdapter;

	@Autowired
	private JpaTecnicoRepositoryAdapter tecnicoRepositoryAdapter;

	@Autowired
	private JpaSolicitudRepository jpaSolicitudRepository;

	@Autowired
	private JpaTecnicoRepository jpaTecnicoRepository;

	@BeforeEach
	void limpiarDatos() {
		jpaSolicitudRepository.deleteAll();
		jpaTecnicoRepository.deleteAll();
	}

	@Test
	void debeCubrirServicioYAdaptersConPersistenciaReal() {
		TecnicoEntity tecnicoEntity = TecnicoEntity.builder()
				.nombre("Ana")
				.estado(EstadoTecnico.ACTIVO)
				.build();
		tecnicoEntity = jpaTecnicoRepository.save(tecnicoEntity);

		Solicitud solicitud = new Solicitud(null, EstadoSolicitud.PROCESANDO, new Date());
		solicitud.setTecnico(Tecnico.builder()
				.id(tecnicoEntity.getId())
				.nombre(tecnicoEntity.getNombre())
				.estado(tecnicoEntity.getEstado())
				.build());

		Solicitud creada = solicitudService.crearSolicitud(solicitud);
		assertNotNull(creada.getId());
		assertEquals(EstadoSolicitud.PROCESANDO, creada.getEstado());

		Solicitud recuperada = solicitudService.obtenerPorId(creada.getId());
		assertEquals(creada.getId(), recuperada.getId());
		assertEquals("Ana", recuperada.getTecnico().getNombre());

		List<Solicitud> solicitudes = solicitudService.listarTodas();
		assertEquals(1, solicitudes.size());

		solicitudService.asignarTecnico(creada.getId(), tecnicoEntity.getId());
		assertEquals("Ana", solicitudService.obtenerPorId(creada.getId()).getTecnico().getNombre());

		solicitudService.cambiarEstado(creada.getId(), EstadoSolicitud.CERRADA);
		assertEquals(EstadoSolicitud.CERRADA, solicitudService.obtenerPorId(creada.getId()).getEstado());

		solicitudService.reabrirSolicitud(creada.getId());
		assertEquals(EstadoSolicitud.PROCESANDO, solicitudService.obtenerPorId(creada.getId()).getEstado());
	}

	@Test
	void debeCubrirMapeoDeSolicitudSinTecnicoYFechaNula() {
		Solicitud solicitud = new Solicitud();
		solicitud.setEstado(EstadoSolicitud.ABIERTA);
		solicitud.setFechaCreacion(null);

		Solicitud guardada = solicitudRepositoryAdapter.save(solicitud);
		assertNotNull(guardada.getId());

		Solicitud recuperada = solicitudRepositoryAdapter.findById(guardada.getId()).orElseThrow();
		assertEquals(EstadoSolicitud.ABIERTA, recuperada.getEstado());
		assertNull(recuperada.getFechaCreacion());
		assertNull(recuperada.getTecnico());

		assertFalse(solicitudRepositoryAdapter.findAll().isEmpty());
	}

	@Test
	void debeCubrirBusquedaDeTecnicoPresenteYAusente() {
		TecnicoEntity tecnicoEntity = TecnicoEntity.builder()
				.nombre("Luis")
				.estado(EstadoTecnico.ACTIVO)
				.build();
		tecnicoEntity = jpaTecnicoRepository.save(tecnicoEntity);

		Tecnico tecnico = tecnicoRepositoryAdapter.findById(tecnicoEntity.getId()).orElseThrow();
		assertEquals("Luis", tecnico.getNombre());

		assertFalse(tecnicoRepositoryAdapter.findById(9999L).isPresent());
	}
}