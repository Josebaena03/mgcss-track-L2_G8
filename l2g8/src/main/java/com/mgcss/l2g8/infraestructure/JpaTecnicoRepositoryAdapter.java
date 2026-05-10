package com.mgcss.l2g8.infraestructure;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mgcss.l2g8.domain.Tecnico;

@Component
public class JpaTecnicoRepositoryAdapter implements TecnicoRepository {

	private final JpaTecnicoRepository jpaTecnicoRepository;

	public JpaTecnicoRepositoryAdapter(JpaTecnicoRepository jpaTecnicoRepository) {
		this.jpaTecnicoRepository = jpaTecnicoRepository;
	}

	@Override
	public Optional<Tecnico> findById(Long id) {
		return jpaTecnicoRepository.findById(id).map(entity -> Tecnico.builder()
				.id(entity.getId())
				.nombre(entity.getNombre())
				.estado(entity.getEstado())
				.build());
	}
}