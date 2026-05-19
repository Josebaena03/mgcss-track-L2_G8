package com.mgcss.l2g8.infraestructure;

import java.util.List;
import java.util.Optional;

import com.mgcss.l2g8.domain.Tecnico;

public interface TecnicoRepository {

    Optional<Tecnico> findById(Long id);

    List<Tecnico> findAll();

    Tecnico save(Tecnico tecnico);
}