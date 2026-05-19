package com.mgcss.l2g8.infraestructure;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                return jpaTecnicoRepository.findById(id).map(this::toDomain);
        }

        @Override
        public List<Tecnico> findAll() {
                return jpaTecnicoRepository.findAll().stream()
                                .map(this::toDomain)
                                .collect(Collectors.toList());
        }

        @Override
        public Tecnico save(Tecnico tecnico) {
                TecnicoEntity entity = TecnicoEntity.builder()
                                .id(tecnico.getId())
                                .nombre(tecnico.getNombre())
                                .estado(tecnico.getEstado())
                                .build();
                TecnicoEntity saved = jpaTecnicoRepository.save(entity);
                return toDomain(saved);
        }

        private Tecnico toDomain(TecnicoEntity entity) {
                return Tecnico.builder()
                                .id(entity.getId())
                                .nombre(entity.getNombre())
                                .estado(entity.getEstado())
                                .build();
        }
}
