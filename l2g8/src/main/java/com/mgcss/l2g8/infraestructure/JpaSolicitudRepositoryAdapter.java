package com.mgcss.l2g8.infraestructure;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mgcss.l2g8.domain.Solicitud;
import com.mgcss.l2g8.domain.Tecnico;

@Component
public class JpaSolicitudRepositoryAdapter implements SolicitudRepository {

	private final JpaSolicitudRepository jpaSolicitudRepository;

	public JpaSolicitudRepositoryAdapter(JpaSolicitudRepository jpaSolicitudRepository) {
		this.jpaSolicitudRepository = jpaSolicitudRepository;
	}

	@Override
	public Solicitud save(Solicitud solicitud) {
		SolicitudEntity entity = toEntity(solicitud);
		SolicitudEntity saved = jpaSolicitudRepository.save(entity);
		return toDomain(saved);
	}

	@Override
	public Optional<Solicitud> findById(Long id) {
		return jpaSolicitudRepository.findById(id).map(this::toDomain);
	}

	@Override
	public List<Solicitud> findAll() {
		return jpaSolicitudRepository.findAll().stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
	}

	private SolicitudEntity toEntity(Solicitud solicitud) {
		if (solicitud == null) {
			return null;
		}

		return SolicitudEntity.builder()
				.id(solicitud.getId())
				.estado(solicitud.getEstado())
				.fechaCreacion(toLocalDateTime(solicitud.getFechaCreacion()))
				.tecnico(solicitud.getTecnico() != null ? toTecnicoEntity(solicitud.getTecnico()) : null)
				.build();
	}

	private Solicitud toDomain(SolicitudEntity entity) {
		if (entity == null) {
			return null;
		}

		Solicitud solicitud = new Solicitud();
		solicitud.setId(entity.getId());
		solicitud.setEstado(entity.getEstado());
		solicitud.setFechaCreacion(toDate(entity.getFechaCreacion()));
		solicitud.setTecnico(toDomain(entity.getTecnico()));
		return solicitud;
	}

	private TecnicoEntity toTecnicoEntity(Tecnico tecnico) {
		return TecnicoEntity.builder()
				.id(tecnico.getId())
				.nombre(tecnico.getNombre())
				.estado(tecnico.getEstado())
				.build();
	}

	private Tecnico toDomain(TecnicoEntity entity) {
		if (entity == null) {
			return null;
		}

		return Tecnico.builder()
				.id(entity.getId())
				.nombre(entity.getNombre())
				.estado(entity.getEstado())
				.build();
	}

	private LocalDateTime toLocalDateTime(Date fecha) {
		if (fecha == null) {
			return null;
		}

		return LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
	}

	private Date toDate(LocalDateTime fecha) {
		if (fecha == null) {
			return null;
		}

		return Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
	}
}