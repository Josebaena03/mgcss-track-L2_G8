package com.mgcss.l2g8.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.l2g8.controller.dto.SolicitudRequestDTO;
import com.mgcss.l2g8.controller.dto.SolicitudResponseDTO;
import com.mgcss.l2g8.domain.Solicitud;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.service.SolicitudService;


@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

	private final SolicitudService solicitudService;

	public SolicitudController(SolicitudService solicitudService) {
		this.solicitudService = solicitudService;
	}

	@PostMapping
	public ResponseEntity<SolicitudResponseDTO> crear() {
		Solicitud nueva = new Solicitud(null, EstadoSolicitud.ABIERTA, new Date());
		Solicitud creada = solicitudService.crearSolicitud(nueva);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(creada));
	}

	@GetMapping("/{id}")
	public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable Long id) {
		Solicitud solicitud = solicitudService.obtenerPorId(id);
		return ResponseEntity.ok(mapToDTO(solicitud));
	}

	@GetMapping
	public ResponseEntity<List<SolicitudResponseDTO>> listar() {
		List<SolicitudResponseDTO> solicitudes = solicitudService.listarTodas().stream()
				.map(this::mapToDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(solicitudes);
	}

	@PutMapping("/{id}/tecnico")
	public ResponseEntity<Void> asignar(@PathVariable Long id, @RequestBody SolicitudRequestDTO req) {
		solicitudService.asignarTecnico(id, req.getTecnicoId());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}/estado")
	public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestBody SolicitudRequestDTO req) {
		solicitudService.cambiarEstado(id, req.getNuevoEstado());
		return ResponseEntity.ok().build();
	}

	@org.springframework.web.bind.annotation.PatchMapping("/{id}/reabrir")
	public ResponseEntity<Void> reabrir(@PathVariable Long id) {
		solicitudService.reabrirSolicitud(id);
		return ResponseEntity.ok().build();
	}

	private SolicitudResponseDTO mapToDTO(Solicitud solicitud) {
		return SolicitudResponseDTO.builder()
				.id(solicitud.getId())
				.estado(solicitud.getEstado())
				.fechaCreacion(solicitud.getFechaCreacion())
				.nombreTecnico(solicitud.getTecnico() != null ? solicitud.getTecnico().getNombre() : "Pendiente")
				.build();
	}
}