package com.mgcss.l2g8.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.mgcss.l2g8.domain.Solicitud;
import com.mgcss.l2g8.domain.Tecnico;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;
import com.mgcss.l2g8.service.SolicitudService;

@WebMvcTest(SolicitudController.class)
class SolicitudControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private SolicitudService solicitudService;

	@Test
	void debeRetornar201AlCrearSolicitud() throws Exception {
		Solicitud solicitudMock = new Solicitud(1L, EstadoSolicitud.ABIERTA, new Date());
		when(solicitudService.crearSolicitud(any())).thenReturn(solicitudMock);

		mockMvc.perform(post("/api/solicitudes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.estado").value("ABIERTA"));

		verify(solicitudService).crearSolicitud(any());
	}

	@Test
	void debeConsultarSolicitudPorId() throws Exception {
		Solicitud solicitudMock = new Solicitud(2L, EstadoSolicitud.PROCESANDO, new Date());
		solicitudMock.setTecnico(Tecnico.builder().id(3L).nombre("Ana").estado(EstadoTecnico.ACTIVO).build());
		when(solicitudService.obtenerPorId(2L)).thenReturn(solicitudMock);

		mockMvc.perform(get("/api/solicitudes/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.nombreTecnico").value("Ana"));
	}

	@Test
	void debeListarSolicitudes() throws Exception {
		Solicitud solicitudMock = new Solicitud(4L, EstadoSolicitud.ABIERTA, new Date());
		when(solicitudService.listarTodas()).thenReturn(List.of(solicitudMock));

		mockMvc.perform(get("/api/solicitudes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id").value(4));
	}

	@Test
	void debeAsignarTecnico() throws Exception {
		mockMvc.perform(put("/api/solicitudes/5/tecnico")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"tecnicoId\":7}"))
				.andExpect(status().isOk());

		verify(solicitudService).asignarTecnico(5L, 7L);
	}

	@Test
	void debeCambiarEstado() throws Exception {
		mockMvc.perform(put("/api/solicitudes/6/estado")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nuevoEstado\":\"PROCESANDO\"}"))
				.andExpect(status().isOk());

		verify(solicitudService).cambiarEstado(6L, EstadoSolicitud.PROCESANDO);
	}

	@Test
	void debeReabrirSolicitud() throws Exception {
		mockMvc.perform(patch("/api/solicitudes/7/reabrir"))
				.andExpect(status().isOk());

		verify(solicitudService).reabrirSolicitud(7L);
	}
}