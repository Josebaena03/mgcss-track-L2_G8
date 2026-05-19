package com.mgcss.l2g8.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.l2g8.controller.dto.TecnicoRequestDTO;
import com.mgcss.l2g8.controller.dto.TecnicoResponseDTO;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;
import com.mgcss.l2g8.service.TecnicoService;

@ExtendWith(MockitoExtension.class)
class TecnicoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TecnicoService tecnicoService;

    @InjectMocks
    private TecnicoController tecnicoController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tecnicoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void crearTecnico_ReturnCreatedAndTecnico() throws Exception {
        TecnicoRequestDTO request = new TecnicoRequestDTO();
        request.setNombre("Pedro");
        request.setEstado(EstadoTecnico.ACTIVO);

        TecnicoResponseDTO response = new TecnicoResponseDTO(1L, "Pedro", EstadoTecnico.ACTIVO);

        when(tecnicoService.crearTecnico(any(TecnicoRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/tecnicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pedro"))
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    @Test
    void obtenerTodos_ReturnOkAndList() throws Exception {
        TecnicoResponseDTO response1 = new TecnicoResponseDTO(1L, "Pedro", EstadoTecnico.ACTIVO);
        TecnicoResponseDTO response2 = new TecnicoResponseDTO(2L, "Juan", EstadoTecnico.INACTIVO);
        List<TecnicoResponseDTO> lista = Arrays.asList(response1, response2);

        when(tecnicoService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/tecnicos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Pedro"))
                .andExpect(jsonPath("$[0].estado").value("ACTIVO"))
                .andExpect(jsonPath("$[1].nombre").value("Juan"));
    }
}
