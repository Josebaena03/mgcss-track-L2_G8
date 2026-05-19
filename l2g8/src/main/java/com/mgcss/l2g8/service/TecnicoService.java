package com.mgcss.l2g8.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mgcss.l2g8.controller.dto.TecnicoRequestDTO;
import com.mgcss.l2g8.controller.dto.TecnicoResponseDTO;
import com.mgcss.l2g8.domain.Tecnico;
import com.mgcss.l2g8.infraestructure.TecnicoRepository;

@Service
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public TecnicoResponseDTO crearTecnico(TecnicoRequestDTO req) {
        Tecnico tecnico = Tecnico.builder()
                .nombre(req.getNombre())
                .estado(req.getEstado())
                .build();

        Tecnico saved = tecnicoRepository.save(tecnico);

        return new TecnicoResponseDTO(saved.getId(), saved.getNombre(), saved.getEstado());
    }

    public List<TecnicoResponseDTO> obtenerTodos() {
        return tecnicoRepository.findAll().stream()
                .map(t -> new TecnicoResponseDTO(t.getId(), t.getNombre(), t.getEstado()))
                .collect(Collectors.toList());
    }
}
