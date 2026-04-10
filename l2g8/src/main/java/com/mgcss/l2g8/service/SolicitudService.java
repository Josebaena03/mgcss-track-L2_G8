package com.mgcss.l2g8.service;

import com.mgcss.l2g8.domain.Solicitud;
import com.mgcss.l2g8.domain.Tecnico;
import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;

public class SolicitudService {

	public void asignarTecnico(Solicitud solicitud, Tecnico tecnico) {
		if (solicitud == null) {
			throw new IllegalArgumentException("La solicitud es obligatoria.");
		}

		if (tecnico == null) {
			throw new IllegalArgumentException("El tecnico es obligatorio.");
		}

		if (solicitud.getEstado() == EstadoSolicitud.CERRADA) {
			throw new IllegalStateException("No se puede asignar tecnico a una solicitud cerrada.");
		}

		if (tecnico.getEstado() != EstadoTecnico.ACTIVO) {
			throw new IllegalStateException("El técnico no se puede asignar porque no está activo.");
		}

		solicitud.asignarTecnico(tecnico);
	}

	public void cerrarSolicitud(Solicitud solicitud) {
		if (solicitud == null) {
			throw new IllegalArgumentException("La solicitud es obligatoria.");
		}

		solicitud.cerrarSolicitud();
	}
}
