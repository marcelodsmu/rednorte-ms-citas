package com.rednorte.ms_citas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CitaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CitaService citaService;

    @InjectMocks
    private CitaController citaController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(citaController).build();
    }

    @Test
    void listarCitasShouldReturnOk() throws Exception {
        Cita cita = new Cita();
        cita.setId(1L);
        cita.setFecha("2026-06-22");
        when(citaService.findAll()).thenReturn(List.of(cita));

        mockMvc.perform(get("/api/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fecha").value("2026-06-22"));
    }

    @Test
    void obtenerCitaShouldReturnOkWhenFound() throws Exception {
        Cita cita = new Cita();
        cita.setId(1L);
        cita.setEspecialidad("Cardiología");
        when(citaService.findById(1L)).thenReturn(Optional.of(cita));

        mockMvc.perform(get("/api/citas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.especialidad").value("Cardiología"));
    }

    @Test
    void crearCitaShouldReturnCreated() throws Exception {
        Cita cita = new Cita();
        cita.setEspecialidad("Odontología");
        when(citaService.save(any(Cita.class))).thenReturn(cita);

        mockMvc.perform(post("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cita)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.especialidad").value("Odontología"));
    }

    @Test
    void actualizarCitaShouldReturnOkWhenFound() throws Exception {
        Cita cita = new Cita();
        cita.setEspecialidad("Dermatología");
        when(citaService.update(eq(1L), any(Cita.class))).thenReturn(Optional.of(cita));

        mockMvc.perform(put("/api/citas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cita)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.especialidad").value("Dermatología"));
    }

    @Test
    void eliminarCitaShouldReturnNoContentWhenDeleted() throws Exception {
        when(citaService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/citas/1"))
                .andExpect(status().isNoContent());
    }
}
