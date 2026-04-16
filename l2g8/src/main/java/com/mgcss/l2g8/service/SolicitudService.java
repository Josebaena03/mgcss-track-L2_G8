package com.mgcss.l2g8.service;

import com.mgcss.l2g8.domain.Solicitud;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.Tecnico;
import com.mgcss.l2g8.infraestructure.SolicitudRepository;
import com.mgcss.l2g8.infraestructure.TecnicoRepository;

public class SolicitudService {

	private final SolicitudRepository solicitudRepository;
	private final TecnicoRepository tecnicoRepository;

	public SolicitudService(SolicitudRepository solicitudRepository, TecnicoRepository tecnicoRepository) {
		if (solicitudRepository == null) {
			throw new IllegalArgumentException("El repositorio de solicitudes es obligatorio.");
		}

		if (tecnicoRepository == null) {
			throw new IllegalArgumentException("El repositorio de tecnicos es obligatorio.");
		}

		this.solicitudRepository = solicitudRepository;
		this.tecnicoRepository = tecnicoRepository;
	}

	public Solicitud crearSolicitud(Solicitud solicitud) {
		if (solicitud == null) {
			throw new IllegalArgumentException("La solicitud es obligatoria.");
		}

		return solicitudRepository.save(solicitud);
	}

	public void asignarTecnico(Long solicitudId, Long tecnicoId) {
		if (solicitudId == null) {
			throw new IllegalArgumentException("El id de la solicitud es obligatorio.");
		}

		if (tecnicoId == null) {
			throw new IllegalArgumentException("El id del tecnico es obligatorio.");
		}

		Solicitud solicitud = solicitudRepository.findById(solicitudId)
				.orElseThrow(() -> new IllegalArgumentException("La solicitud no existe."));
		Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
				.orElseThrow(() -> new IllegalArgumentException("El tecnico no existe."));

		solicitud.asignarTecnico(tecnico);
		solicitudRepository.save(solicitud);
	}

	public void cambiarEstado(Long solicitudId, EstadoSolicitud nuevoEstado) {
		if (solicitudId == null) {
			throw new IllegalArgumentException("El id de la solicitud es obligatorio.");
		}

		Solicitud solicitud = solicitudRepository.findById(solicitudId)
				.orElseThrow(() -> new IllegalArgumentException("La solicitud no existe."));

		solicitud.cambiarEstado(nuevoEstado);
		solicitudRepository.save(solicitud);
	}
}
