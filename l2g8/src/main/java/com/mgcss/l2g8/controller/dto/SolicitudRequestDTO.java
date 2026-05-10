package com.mgcss.l2g8.controller.dto;

import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudRequestDTO {

	private Long tecnicoId;
	private EstadoSolicitud nuevoEstado;
	private String descripcion;
}