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
		validarDependencias(solicitudRepository, tecnicoRepository);

		this.solicitudRepository = solicitudRepository;
		this.tecnicoRepository = tecnicoRepository;
	}

	public Solicitud crearSolicitud(Solicitud solicitud) {
		validarSolicitud(solicitud);

		return solicitudRepository.save(solicitud);
	}

	public void asignarTecnico(Long solicitudId, Long tecnicoId) {
		validarIdentificadores(solicitudId, tecnicoId);

		Solicitud solicitud = buscarSolicitud(solicitudId);
		Tecnico tecnico = buscarTecnico(tecnicoId);

		solicitud.asignarTecnico(tecnico);
		solicitudRepository.save(solicitud);
	}

	public void cambiarEstado(Long solicitudId, EstadoSolicitud nuevoEstado) {
		validarSolicitudId(solicitudId);

		Solicitud solicitud = buscarSolicitud(solicitudId);

		solicitud.cambiarEstado(nuevoEstado);
		solicitudRepository.save(solicitud);
	}

	private void validarDependencias(SolicitudRepository solicitudRepository, TecnicoRepository tecnicoRepository) {
		if (solicitudRepository == null) {
			throw new IllegalArgumentException("El repositorio de solicitudes es obligatorio.");
		}

		if (tecnicoRepository == null) {
			throw new IllegalArgumentException("El repositorio de tecnicos es obligatorio.");
		}
	}

	private void validarSolicitud(Solicitud solicitud) {
		if (solicitud == null) {
			throw new IllegalArgumentException("La solicitud es obligatoria.");
		}
	}

	private void validarIdentificadores(Long solicitudId, Long tecnicoId) {
		validarSolicitudId(solicitudId);
		if (tecnicoId == null) {
			throw new IllegalArgumentException("El id del tecnico es obligatorio.");
		}
	}

	private void validarSolicitudId(Long solicitudId) {
		if (solicitudId == null) {
			throw new IllegalArgumentException("El id de la solicitud es obligatorio.");
		}
	}

	private Solicitud buscarSolicitud(Long solicitudId) {
		return solicitudRepository.findById(solicitudId)
				.orElseThrow(() -> new IllegalArgumentException("La solicitud no existe."));
	}

	private Tecnico buscarTecnico(Long tecnicoId) {
		return tecnicoRepository.findById(tecnicoId)
				.orElseThrow(() -> new IllegalArgumentException("El tecnico no existe."));
	}
}
