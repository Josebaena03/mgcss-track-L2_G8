package com.mgcss.l2g8.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.l2g8.controller.dto.TecnicoRequestDTO;
import com.mgcss.l2g8.controller.dto.TecnicoResponseDTO;
import com.mgcss.l2g8.service.TecnicoService;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    public ResponseEntity<TecnicoResponseDTO> crear(@RequestBody TecnicoRequestDTO req) {
        TecnicoResponseDTO response = tecnicoService.crearTecnico(req);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TecnicoResponseDTO>> obtenerTodos() {
        List<TecnicoResponseDTO> response = tecnicoService.obtenerTodos();
        return ResponseEntity.ok(response);
    }
}
